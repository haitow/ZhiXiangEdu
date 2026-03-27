import request from "@/common/request.js"
export function commonDownload(params) {
    return request({
        url: '/common/download?name='+params,
        method: 'get'
    })
	
}
// export function commonUpload(params) {
//     return request({
//         url: '/common/upload?name='+params,
//         method: 'post'
//     })
	
// }