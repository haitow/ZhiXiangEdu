import request from "@/common/request.js"

//根据标签获取课程列表
export function getClassList(tags){
	return request({
		url: "/classes/getClassList/"+tags,
		method: "GET",
	})
}
//通过会员卡tag查询适用课程
export function getClassByCardTags(tags){
	return request({
		url: "/classes/getClassByCardTags?tags="+tags,
		method: "GET",
		
	})
}
//通过课程id获取课程详情
export function getClassByClassId(id){
	return request({
		url: "/classes/"+id,
		method: "GET",
		
	})
}


//通过teacherId新建课程
export function addClasses(params){
	return request({
		url: "/classes",
		method: "post",
		data:params
	})
}

//查询已选此课会员列表
// export function getStudentByClassId(classId){
// 	return request({
// 		url: "/classes/user/"+classId,
// 		method: "get"
// 	})
// }

export function getStudentByClassId(classId){
	return request({
		url: "/classes/getUserByClassesId/"+classId,
		method: "get"
	})
}
