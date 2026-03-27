import request from "@/common/request.js"

//获取课程列表
export function pay(orderId){
	return request({
		url: "/alipay/pay?orderId="+orderId,
		method: "get",
	})
}