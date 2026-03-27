import request from "@/common/request.js"

//获取老师列表
export function getTeacherList(type){
	return request({
		url: "/user/teacher",
		method: "GET"
	})
}
// 通过id获取用户详情信息
export function getUserById(id){
	return request({
		url:"/user/user/"+id,
		method:"GET"
	})
}
// 用户选课
export function chooseClasses(params){
	return request({
		url: "/userClasses/chooseClasses",
		method: "POST",
		data:{...params}
	})
}
// 用户办卡
export function chooseCard(cardId,params){
	return request({
		url: "/user/user/saveCardById/"+cardId,
		method: "POST",
		data:{...params}
	})
}
// 修改用户信息
export function updateUser(params){
	return request({
		url: "/user/user",
		method: "put",
		data:{...params}
	})
}
// 通过用户Id获取已办会员卡列表
export function getSelectedCardByUserId(userId){
	return request({
		url: "/user/userCards/"+userId,
		method: "get"
	})
}
// 通过用户Id获取已选课程列表
export function getSelectedClassesByUserId(userId){
	return request({
		url: "/userClasses/"+userId,
		method: "get"
	})
}
// 发送短信获取登录验证码
export function getCode(user){
	return request({
		url: "/user/sendMsg",
		method: "post",
		data:{...user}
	})
}
// 办卡
export function getSelectedCard(userId,cardId){
	return request({
		url: "/user/getSelectedCard?userId="+userId+"&cardId="+cardId,
		method: "get"
	})
}


// 根据课程id获取已选此课会员列表
export function getStudentListByClassId(id){
	return request({
		url: "/userClasses/getClassesByUserId/"+id,
		method: "get"
	})
}
//获取老师总课时
export function getClassesTotalCount(teacherId){
	return request({
		url: "/userClasses/getClassesTotalCount/"+teacherId,
		method: "get"
	})
}
//获取老师月度课时
export function getClassesMouthCount(teacherId){
	return request({
		url: "/userClasses/getMouthTotal/"+teacherId,
		method: "get"
	})
}
export function getTeacherCount(teacherId){
	return request({
		url: "/userClasses/getTeacherCount/"+teacherId,
		method: "get"
	})
}

//修改会员签到状态
export function userAttendance(classId,userId,status){
	return request({
		url: "/userClasses/attendance?classId="+classId+"&userId="+userId+"&status="+status,
		method: "put"
	})
}
//查询老师已选课程
export function getClassByTeacherId(userId){
	return request({
		url: "/user/teacher/classes/"+userId,
		method: "get"
	})
}



