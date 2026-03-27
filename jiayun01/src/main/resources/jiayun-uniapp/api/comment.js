import request from "@/common/request.js"

//根据商品id获取评论列表
export function getCommentsByShopId(shopId){
	return request({
		url: "/comment/getCommentsByShopId/"+shopId,
		method: "GET",
	
	})
}
//发表评论
export function submitComment(param){
	return request({
		url: "/comment/submitComment",
		method: "post",
		data:param
	})
}
