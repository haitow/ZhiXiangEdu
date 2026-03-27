// 查询列表接口
const getOrdersPage = (params) => {
    return $axios({
        url: '/orders/page',
        method: 'get',
        params
    })
}
//查询拥有该卡的用户
const getByIdWithUser = (id) => {
    return $axios({
        url: `/orders/user/${id}`,
        method: 'get'
    })
}
//查询tags表 拼接选择的tags标签，组合成一个字符串  /tags  get [xx,xx,xx]
function getAllTagsName(){
    return $axios({
        url: '/tags',
        method: 'get'
    })
}

// 删除接口
const deleteOrders = (id) => {
    return $axios({
        url: '/orders',
        method: 'delete',
        params: { id }
    })
}

// 修改接口
const editOrders = (params) => {
    return $axios({
        url: '/orders',
        method: 'put',
        data: { ...params }
    })
}

// 新增接口
const addOrders = (params) => {
    return $axios({
        url: '/orders',
        method: 'post',
        data: { ...params }
    })
}

// 查询详情
const queryOrdersById = (id) => {
    return $axios({
        url: `/orders/${id}`,
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
function enableOrDisableOrders (params) {
    return $axios({
        url: '/orders',
        method: 'put',
        data: { ...params }
    })
}
