import request from "@/common/request.js"

//获取通知列表
export function getNoticeList(){
	return request({
		url: "/notice",
		method: "GET"
	})
}