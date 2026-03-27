import request from "@/common/request.js"
//获取课程列表
export function getActivityList(){
	return request({
		url: "/activity/getActivityList",
		method: "GET",
	})
}