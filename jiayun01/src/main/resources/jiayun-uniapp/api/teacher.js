import request from "@/common/request.js"

export function getTeacherListPage(page,pageSize){
	return request({
		url: "/user/teacher/page?page="+page+"&pageSize="+pageSize,
		method: "GET"	
	})
}
// 通过teacherId新建课程
export function addClasses(params){
	return request({
		url: "/classes",
		method: "post",
		data:params
	})
}
