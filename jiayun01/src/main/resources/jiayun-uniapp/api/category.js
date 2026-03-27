import request from '@/common/request.js'
//获取分类列表
export function getCategoryList(){
	return request({
		url: "/category",
		method: "GET"
	})
}