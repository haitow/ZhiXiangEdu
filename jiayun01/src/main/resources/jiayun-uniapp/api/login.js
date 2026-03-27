import request from '@/common/request.js'

// 登录方法
export function login(phone, code,type) {
  const data = {
    phone,
    code,
	type
  }
  return request({
    'url': '/user/login',
    'method': 'post',
    'data': data
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    'url': '/getInfo',
    'method': 'get'
  })
}

// 退出方法
export function logout() {
  return request({
    'url': '/logout',
    'method': 'post'
  })
}
