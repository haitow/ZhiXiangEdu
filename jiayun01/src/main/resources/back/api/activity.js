// 查询列表接口
const getActivityPage = (params) => {
    return $axios({
        url: '/activity/page',
        method: 'get',
        params
    })
}

// 删除接口
const deleteActivity = (id) => {
    return $axios({
        url: '/activity',
        method: 'delete',
        params: { id }
    })
}

// 修改接口
const editActivity = (params) => {
    return $axios({
        url: '/activity',
        method: 'put',
        data: { ...params }
    })
}

// 新增接口
const addActivity = (params) => {
    return $axios({
        url: '/activity',
        method: 'post',
        data: { ...params }
    })
}

// 查询详情
const queryActivityById = (id) => {
    return $axios({
        url: `/activity/${id}`,
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
function enableOrDisableActivity (params) {
    return $axios({
        url: '/activity/change',
        method: 'put',
        data: { ...params }
    })
}
