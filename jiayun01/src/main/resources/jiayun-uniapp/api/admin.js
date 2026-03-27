import request from "@/common/request.js"
//获取课程列表
export function getAdminList(){
	return request({
		url: "/admin",
		method: "GET",
	})
}