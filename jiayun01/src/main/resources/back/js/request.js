(function (win) {
  axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
  const service = axios.create({
    baseURL: '',
    timeout: 1000000
  })

  let isRefreshing = false
  let refreshSubscribers = []

  function subscribeTokenRefresh(cb) {
    refreshSubscribers.push(cb)
  }

  function onTokenRefreshed(newAccessToken, newRefreshToken) {
    refreshSubscribers.forEach(cb => cb(newAccessToken, newRefreshToken))
    refreshSubscribers = []
  }

  function getAccessToken() {
    return window.localStorage.getItem("accessToken")
  }

  function getRefreshToken() {
    return window.localStorage.getItem("refreshToken")
  }

  function setTokens(accessToken, refreshToken) {
    window.localStorage.setItem("accessToken", accessToken)
    window.localStorage.setItem("refreshToken", refreshToken)
  }

  function clearTokens() {
    window.localStorage.removeItem("accessToken")
    window.localStorage.removeItem("refreshToken")
    window.localStorage.removeItem("userInfo")
  }

  service.interceptors.request.use(config => {
    var accessToken = getAccessToken()
    if (accessToken != null && accessToken !== "") {
      config.headers['Authorization'] = 'Bearer ' + accessToken
    }

    // 文件上传时不设置 Content-Type，让浏览器自动设置 multipart/form-data
    if (config.data instanceof FormData) {
      delete config.headers['Content-Type']
    }

    if (config.method === 'get' && config.params) {
      let url = config.url + '?';
      for (const propName of Object.keys(config.params)) {
        const value = config.params[propName];
        var part = encodeURIComponent(propName) + "=";
        if (value !== null && typeof(value) !== "undefined") {
          if (typeof value === 'object') {
            for (const key of Object.keys(value)) {
              let params = propName + '[' + key + ']';
              var subPart = encodeURIComponent(params) + "=";
              url += subPart + encodeURIComponent(value[key]) + "&";
            }
          } else {
            url += part + encodeURIComponent(value) + "&";
          }
        }
      }
      url = url.slice(0, -1);
      config.params = {};
      config.url = url;
    }
    return config
  }, error => {
      console.log(error);
      Promise.reject(error)
  });

  service.interceptors.response.use(res => {
    console.log('---响应拦截器---',res);
    const code = res.data.code;
    const msg = res.data.msg;
    console.log('---code---',code);
    if (res.data.code == 0 && res.data.msg == "NOTLOGIN") {
      clearTokens();
      window.top.location.href = '../login/login.html'
    } else if (res.data.code == 0 && res.data.msg == "TOKEN_EXPIRED") {
      const config = res.config

      if (!isRefreshing) {
        isRefreshing = true
        var refreshToken = getRefreshToken()
        if (!refreshToken) {
          clearTokens()
          window.top.location.href = '../login/login.html'
          return Promise.reject(res.data)
        }

        return axios.post('/auth/refresh', {
          refreshToken: refreshToken
        }).then(response => {
          if (response.data.code == 1) {
            var newAccessToken = response.data.data.accessToken
            var newRefreshToken = response.data.data.refreshToken
            setTokens(newAccessToken, newRefreshToken)
            onTokenRefreshed(newAccessToken, newRefreshToken)

            config.headers['Authorization'] = 'Bearer ' + newAccessToken
            return service(config)
          } else {
            clearTokens()
            window.top.location.href = '../login/login.html'
            return Promise.reject(response.data)
          }
        }).catch(err => {
          clearTokens()
          window.top.location.href = '../login/login.html'
          return Promise.reject(err)
        }).finally(() => {
          isRefreshing = false
        })
      }

      return new Promise((resolve) => {
        subscribeTokenRefresh((newAccessToken, newRefreshToken) => {
          config.headers['Authorization'] = 'Bearer ' + newAccessToken
          resolve(service(config))
        })
      })
    } else {
      return res.data
    }
  },
  error => {
    console.log('err' + error);
    let {message} = error;
    var code = message.substr(message.length - 3);
    if(code == 401){
      clearTokens()
      window.top.location.href = '../login/login.html'
    }
    if(code == 404){
      console.log(code + "功能尚未完善")
      window.ELEMENT.Message({
        message: code + "功能尚未完善",
        type: 'error',
        duration: 5 * 1000
      })
    }
    return Promise.reject(error)
  })
  window.$axios = service
})(window);
