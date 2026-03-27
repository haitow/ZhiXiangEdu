(function (win) {
  axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
  // 创建axios实例
  const service = axios.create({
    // axios中请求配置有baseURL选项，表示请求URL公共部分
    baseURL: 'http://localhost:8082',
    // baseURL: 'http://192.168.138.50:8080',
    // 超时
    timeout: 1000000
  })
  // request拦截器
  service.interceptors.request.use(config => {
    // 是否需要设置 token
    // 让每个请求携带自定义token 请根据实际情况自行修改
    var userInfo = window.localStorage.getItem("userInfo")
    //避免没有登录情况
    if (userInfo != null && userInfo != ""){
      config.headers['auth'] = JSON.parse(userInfo)
    }

    // get请求映射params参数
    if (config.method === 'get' && config.params) {
      //localhost:8080/user/login?username=zhangsan&password=123456
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

  /*
  *1. 响应拦截器
  *  use   200
  * error  4xx
  * 后台访问一个不存在的mapper  404
  *
  * */

  // 响应拦截器
  service.interceptors.response.use(res => {
    console.log('---响应拦截器---',res);
	  console.log(service);
      //未设置状态码则默认=成功状态
      const code = res.data.code;
      //获取错误信息
      const msg = res.data.msg;
      console.log('---code---',code);
      if (res.data.code == 0 && res.data.msg == "NOTLOGIN") {// 返回登录页面
        console.log('---/back/page/login/login.html---',code);
        localStorage.removeItem('userInfo');
        window.top.location.href = '../login/login.html'
      } else {
        return res.data
      }
    },
    error => {
      console.log('err' + error);
      let {message} = error;
      var code = message.substr(message.length - 3);
      if(code == 401){
        console.log(code + "权限不够跳转到登录页面")
        // window.ELEMENT.Message({
        //   message: code + "权限不够跳转到登录页面",
        //   type: 'error',
        //   duration: 2 * 1000
        // })
        // window.setTimeout(function (){
          window.top.location.href = '../login/login.html'
        // },2000)
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
    }
  )
  window.$axios = service
})(window);
