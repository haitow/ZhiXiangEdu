import request from "@/common/request.js"

//获取标签列表
export function getTagsList(){
	return request({
		url: "/tags",
		method: "GET",
	})
}