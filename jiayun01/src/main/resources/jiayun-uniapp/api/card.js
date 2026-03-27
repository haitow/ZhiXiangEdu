import request from "@/common/request.js"

//获取会员卡列表(全部查询)
export function getCardList(){
	return request({
		url: "/card/getCardList",
		method: "GET",
		
	})
}
//获取会员卡列表(条件查询)
export function getCardListByType(type){
	return request({
		url: "/card/getCardList?type="+type,
		method: "GET",
	})
}

//通过id会员卡详情信息
export function getCardById(id){
	return request({
		url: "/card/"+id,
		method: "GET",
	})
}
	
