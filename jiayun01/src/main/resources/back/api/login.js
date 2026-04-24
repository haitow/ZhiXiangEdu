function loginApi(data) {
  return $axios({
    'url': '/admin/login',
    'method': 'post',
    data
  })
}

function logoutApi(){
  return $axios({
    'url': '/admin/logout',
    'method': 'get',
  })
}

function registerApi(data) {
  return $axios({
    'url': '/admin/register',
    'method': 'post',
    data
  })
}

function getSecurityQuestionApi(username) {
  return $axios({
    'url': '/admin/security/question',
    'method': 'get',
    params: { username }
  })
}

function verifySecurityAnswerApi(data) {
  return $axios({
    'url': '/admin/security/verify',
    'method': 'post',
    data
  })
}

function resetPasswordApi(data) {
  return $axios({
    'url': '/admin/password/reset',
    'method': 'post',
    data
  })
}
