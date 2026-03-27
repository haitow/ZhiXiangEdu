// 查询列表接口
const getTagsPage = (params) => {
    return $axios({
        url: '/tags/page',
        method: 'get',
        params
    })
}
// 删除接口
const deleteTags = (id) => {
    return $axios({
        url: '/tags',
        method: 'delete',
        params: { id }
    })
}

// 修改接口
const editTags = (params) => {
    return $axios({
        url: '/tags',
        method: 'put',
        data: { ...params }
    })
}

// 新增接口
const addTags = (params) => {
    return $axios({
        url: '/tags',
        method: 'post',
        data: { ...params }
    })
}

// 查询详情
const queryTagsById = (id) => {
    return $axios({
        url: `/tags/${id}`,
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
function enableOrDisableTags (params) {
    return $axios({
        url: '/tags',
        method: 'put',
        data: { ...params }
    })
}