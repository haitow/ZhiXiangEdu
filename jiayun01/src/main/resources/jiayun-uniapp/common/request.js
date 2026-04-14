import axios from "axios";
import { UniAdapter } from "uniapp-axios-adapter";
import { baseUrl } from "../config";

const request = axios.create({
  baseURL: baseUrl,
  timeout: 10000,
  adapter: UniAdapter,
});

let isRefreshing = false;
let refreshSubscribers = [];

function subscribeTokenRefresh(cb) {
  refreshSubscribers.push(cb);
}

function onTokenRefreshed(newAccessToken, newRefreshToken) {
  refreshSubscribers.forEach(cb => cb(newAccessToken, newRefreshToken));
  refreshSubscribers = [];
}

function getAccessToken() {
  return uni.getStorageSync("accessToken");
}

function getRefreshToken() {
  return uni.getStorageSync("refreshToken");
}

function setTokens(accessToken, refreshToken) {
  uni.setStorageSync("accessToken", accessToken);
  uni.setStorageSync("refreshToken", refreshToken);
}

function clearTokens() {
  uni.removeStorageSync("accessToken");
  uni.removeStorageSync("refreshToken");
  uni.removeStorageSync("auth");
  uni.removeStorageSync("userInfo");
}

request.interceptors.request.use((config) => {
  var accessToken = getAccessToken();
  if (accessToken) {
    config.headers['Authorization'] = 'Bearer ' + accessToken;
  }
  return config;
});

request.interceptors.response.use(
  (response) => {
    var data = response.data;
    if (data.code == 0 && data.msg == "NOTLOGIN") {
      clearTokens();
      uni.reLaunch({ url: '/pages/login/login' });
      return Promise.reject(data);
    }
    if (data.code == 0 && data.msg == "TOKEN_EXPIRED") {
      var config = response.config;

      if (!isRefreshing) {
        isRefreshing = true;
        var refreshToken = getRefreshToken();
        if (!refreshToken) {
          clearTokens();
          uni.reLaunch({ url: '/pages/login/login' });
          return Promise.reject(data);
        }

        return axios.post(baseUrl + '/auth/refresh', {
          refreshToken: refreshToken
        }, {
          adapter: UniAdapter
        }).then(res => {
          if (res.data.code == 1) {
            var newAccessToken = res.data.data.accessToken;
            var newRefreshToken = res.data.data.refreshToken;
            setTokens(newAccessToken, newRefreshToken);
            onTokenRefreshed(newAccessToken, newRefreshToken);

            config.headers['Authorization'] = 'Bearer ' + newAccessToken;
            return request(config);
          } else {
            clearTokens();
            uni.reLaunch({ url: '/pages/login/login' });
            return Promise.reject(res.data);
          }
        }).catch(err => {
          clearTokens();
          uni.reLaunch({ url: '/pages/login/login' });
          return Promise.reject(err);
        }).finally(() => {
          isRefreshing = false;
        });
      }

      return new Promise((resolve) => {
        subscribeTokenRefresh((newAccessToken, newRefreshToken) => {
          config.headers['Authorization'] = 'Bearer ' + newAccessToken;
          resolve(request(config));
        });
      });
    }
    return response;
  },
  (error) => {
    if (error.response && error.response.status === 401) {
      clearTokens();
      uni.reLaunch({ url: '/pages/login/login' });
    }
    return Promise.reject(error);
  }
);

export default request;
