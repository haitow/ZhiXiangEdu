import axios from "axios";
import { UniAdapter } from "uniapp-axios-adapter";
import { baseUrl } from "../config";


const request = axios.create({
  baseURL: baseUrl,
  timeout: 10000,
  adapter: UniAdapter,
});

request.interceptors.request.use((config) => {
  //带上token
	var auth = uni.getStorageSync("auth")
	config.headers['auth']=auth
	console.log(config)
	
  return config;
});


request.interceptors.response.use((response) => {
	// var status = response.status
	// console.log("------拦截器生效------");
	// console.log(response)
	// console.log("------拦截器生效------");
	// var auth=uni.getStorageSync('auth')
	// console.log(auth);
	
	// if(uni.getStorageSync('auth')==null){
	// 	uni.navigateTo({
	// 		url:'/pages/login/login'
	// 	})
	// }
	return response;
});

export default request;