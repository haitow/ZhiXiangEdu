export const apiBaseUrl = 'http://localhost:8080'

// 不需要登录的接口
const noToken = [
	'user/login'
];

const request = (method, methodType, data, callback, hideLoading) => {
	// 请求头信息
	var headers = {
		'content-type': 'application/x-www-form-urlencoded'
	};
	
	// 判断是否需要登录
	if (!(noToken.indexOf(method) >= 0)) {
		// 获取用户 token
		let userToken = uni.getStorageSync('x-access-token')
		if (!userToken) {
			uni.navigateTo({
				url: '/pages/login/login'
			})
			return false;
		} else {
			// 将 token 放入请求头中
			headers['x-access-token'] = userToken
		}
	}
	
	// 如果调用接口不明确不显示 loading
	if (!hideLoading) {
		uni.showLoading({
			title: '加载中'
		});
	}
	uni.request({
		url: apiBaseUrl + method,
		data: data,
		header: headers,
		method: methodType,
		success: (response) => {
			if (!hideLoading) {
				uni.hideLoading();
			}
			console.log(response.data)
			const result = response.data
			if (result.code == 500) {
				uni.showToast({
					icon: 'none',
					title: result.message,
					duration: 1500
				})
			} else {
				console.log(result)
				callback(result)
			}
		},
		complete: () => {
			uni.hideLoading();
		},
		fail: (error) => {
			uni.hideLoading();
			if (error && error.response) {
				showError(error.response);
			}
		}
	})
}

const showError = error => {
	let errorMsg = ''
	switch (error.status) {
		case 400:
			errorMsg = '请求参数错误'
			break
		case 401:
			errorMsg = '未授权，请登录'
			break
		case 403:
			errorMsg = '跨域拒绝访问'
			break
		case 404:
			errorMsg = `请求地址出错: ${error.config.url}`
			break
		case 408:
			errorMsg = '请求超时'
			break
		case 500:
			errorMsg = '服务器内部错误'
			break
		case 501:
			errorMsg = '服务未实现'
			break
		case 502:
			errorMsg = '网关错误'
			break
		case 503:
			errorMsg = '服务不可用'
			break
		case 504:
			errorMsg = '网关超时'
			break
		case 505:
			errorMsg = 'HTTP版本不受支持'
			break
		default:
			errorMsg = error.msg
			break
	}

	uni.showToast({
		title: errorMsg,
		icon: 'none',
		duration: 1000,
		complete: function() {
			setTimeout(function() {
				uni.hideToast();
			}, 1000);
		}
	});
}

// 微信登录
export const wxlogin = (data, callback, hideLoading) => request('sys/wxlogin', 'POST', data, callback, hideLoading);