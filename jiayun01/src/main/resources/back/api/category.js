// 查询列表接口
const getCategoryPage = (params) => {
    return $axios({
        url: '/category/page',
        method: 'get',
        params
    })
}
//查询拥有该卡的用户
const getByIdWithUser = (id) => {
    return $axios({
        url: `/category/user/${id}`,
        method: 'get'
    })
}
function getAllCategory(){
    return $axios({
        url: '/category',
        method: 'get'
    })
}

// 删除接口
const deleteCategory = (id) => {
    return $axios({
        url: '/category',
        method: 'delete',
        params: { id }
    })
}

// 修改接口
const editCategory = (params) => {
    return $axios({
        url: '/category',
        method: 'put',
        data: { ...params }
    })
}

// 新增接口
const addCategory = (params) => {
    return $axios({
        url: '/category',
        method: 'post',
        data: { ...params }
    })
}

// 查询详情
const queryCategoryById = (id) => {
    return $axios({
        url: `/category/${id}`,
        method: 'get'
    })
}

// 修改---启用禁用接口
function enableOrDisableCategory (params) {
    return $axios({
        url: '/category',
        method: 'put',
        data: { ...params }
    })
}
