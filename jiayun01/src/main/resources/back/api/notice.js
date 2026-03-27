// 查询列表接口
const getNoticePage = (params) => {
    return $axios({
        url: '/notice/page',
        method: 'get',
        params
    })
}


// 删除接口
const deleteNotice = (id) => {
    return $axios({
        url: '/notice',
        method: 'delete',
        params: { id }
    })
}

// 修改接口
const editNotice = (params) => {
    return $axios({
        url: '/notice',
        method: 'put',
        data: { ...params }
    })
}

// 新增接口
const addNotice = (params) => {
    return $axios({
        url: '/notice',
        method: 'post',
        data: { ...params }
    })
}

// 查询详情
const queryNoticeById = (id) => {
    return $axios({
        url: `/notice/${id}`,
        method: 'get'
    })
}
