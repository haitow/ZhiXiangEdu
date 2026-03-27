// 查询列表接口
const getCardPage = (params) => {
    return $axios({
        url: '/card/page',
        method: 'get',
        params
    })
}
//查询拥有该卡的用户
const getByIdWithUser = (id) => {
    return $axios({
        url: `/card/user/${id}`,
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
const deleteCard = (id) => {
    return $axios({
        url: '/card',
        method: 'delete',
        params: { id }
    })
}

// 修改接口
const editCard = (params) => {
    return $axios({
        url: '/card',
        method: 'put',
        data: { ...params }
    })
}

// 新增接口
const addCard = (params) => {
    return $axios({
        url: '/card',
        method: 'post',
        data: { ...params }
    })
}

// 查询详情
const queryCardById = (id) => {
    return $axios({
        url: `/card/${id}`,
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
function enableOrDisableCard (params) {
    return $axios({
        url: '/card',
        method: 'put',
        data: { ...params }
    })
}
