// 查询列表接口
const getClassPage = (params) => {
    return $axios({
        url: '/classes/page',
        method: 'get',
        params
    })
}
const getClassPage1 = (params) => {
    return $axios({
        url: '/classes/page1',
        method: 'get',
        params
    })
}
// 通过课程id查询其报名的会员
const getByIdWithUserClasses = (id) => {
    return $axios({
        url: `/classes/user/${id}`,
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
//查询 room表 /room  get 下拉框
function getAllRoomName(){
    return $axios({
        url: '/room',
        method: 'get'
    })
}
//查询 user表 /teacher  get 下拉框
function getAllTeacherName(){
    return $axios({
        url: '/user/teacher',
        method: 'get'
    })
}
// 删除接口
const changeStatusTo1 = (id) => {
    return $axios({
        url: '/classes/delete',
        method: 'delete',
        params: { id }
    })
}
const changeStatusTo0 = (id) => {
    return $axios({
        url: '/classes/restore',
        method: 'delete',
        params: { id }
    })
}

// 修改接口
const editClasses = (params) => {
    return $axios({
        url: '/classes',
        method: 'put',
        data: { ...params }
    })
}

// 新增接口
const addClasses = (params) => {
    return $axios({
        url: '/classes',
        method: 'post',
        data: { ...params }
    })
}

// 查询详情
const queryClassesById = (id) => {
    return $axios({
        url: `/classes/${id}`,
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
function enableOrDisableClasses (params) {
    return $axios({
        url: '/classes/change',
        method: 'put',
        data: { ...params }
    })
}