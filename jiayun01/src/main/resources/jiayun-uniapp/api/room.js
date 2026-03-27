import request from "@/common/request.js"
//获取教室列表
export function getRoomList(){
	return request({
		url: "/room",
		method: "GET",
		
	})
}