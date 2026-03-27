import request from '@/common/request.js'

//根据分类id获取商品列表
export function getShopByCategoryId(categoryId){
	return request({
		url: "/shop/getShopByCategoryId/"+categoryId,
		method: "GET",
	})
}
// 根据商品id查询商品详情
export function getShopById(id){
	return request({
		url: "/shop/"+id,
		method: "GET",
		
	})
}