// 查询列表接口
const getRoomPage = (params) => {
    return $axios({
        url: '/room/page',
        method: 'get',
        params
    })
}

// 删除接口
const deleteRoom = (id) => {
    return $axios({
        url: '/room',
        method: 'delete',
        params: { id }
    })
}

// 修改接口
const editRoom = (params) => {
    return $axios({
        url: '/room',
        method: 'put',
        data: { ...params }
    })
}

// 新增接口
const addRoom = (params) => {
    return $axios({
        url: '/room',
        method: 'post',
        data: { ...params }
    })
}

// 查询详情
const queryRoomById = (id) => {
    return $axios({
        url: `/room/${id}`,
        method: 'get'
    })
}
// 修改---启用禁用接口
function enableOrDisableRoom (params) {
    return $axios({
        url: '/room',
        method: 'put',
        data: { ...params }
    })
}