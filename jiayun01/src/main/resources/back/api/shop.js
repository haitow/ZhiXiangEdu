// 查询列表接口
const getShopPage = (params) => {
    return $axios({
        url: '/shop/page',
        method: 'get',
        params
    })
}
//查询拥有该卡的用户
const getById = (id) => {
    return $axios({
        url: `/shop/user/${id}`,
        method: 'get'
    })
}

// 删除接口
const deleteShop = (id) => {
    return $axios({
        url: '/shop',
        method: 'delete',
        params: { id }
    })
}

// 修改接口
const editShop = (params) => {
    return $axios({
        url: '/shop',
        method: 'put',
        data: { ...params }
    })
}

// 新增接口
const addShop = (params) => {
    return $axios({
        url: '/shop',
        method: 'post',
        data: { ...params }
    })
}

// 查询详情
const queryShopById = (id) => {
    return $axios({
        url: `/shop/${id}`,
        method: 'get'
    })
}
function getAllCategory(){
    return $axios({
        url: '/category',
        method: 'get'
    })
}

// 文件down预览
const commonDownload = (params) => {
    return $axios({
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        url: '/common/download',
        method: 'get',
        params
    })
}
// 修改---启用禁用接口
function enableOrDisableShop (params) {
    return $axios({
        url: '/shop/change',
        method: 'put',
        data: { ...params }
    })
}
