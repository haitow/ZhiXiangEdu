import request from '@/common/request.js'
//查询已购商品
export function getOrdersList(param){
	return request({
		url: "/orders",
		method: "GET",
		data:{...param}
	})
}
// 下单
export function placeOrder(params){
	return request({
		url:"/orders/placeOrder",
		method:"post",
		data:{...params}
	})
}
// 删除订单
export function deleteOrder1(id){
	return request({
		url:"/orders?id="+id,
		method:"delete",
	})
}
