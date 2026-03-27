// 查询列表接口
const getTeacherPage = (params) => {
    return $axios({
        url: '/user/teacher/page',
        method: 'get',
        params
    })
}
const getUserPage = (params) => {
    return $axios({
        url: '/user/page',
        method: 'get',
        params
    })
}

//通过id查看老师的所有课程
const getByIdWithClasses = (id) => {
    return $axios({
        url: `/user/teacher/classes/${id}`,
        method: 'get'
    })
}

// 删除接口
const deleteTeacher = (ids) => {
    return $axios({
        url: '/user/teacher',
        method: 'delete',
        params: { ids }
    })
}

// 修改接口
const editTeacher = (params) => {
    return $axios({
        url: '/user/teacher',
        method: 'put',
        data: { ...params }
    })
}

// 新增老师接口
const addTeacher = (params) => {
    return $axios({
        url: '/user/teacher',
        method: 'post',
        data: { ...params }
    })
}
// 新增会员接口
const addUser = (params) => {
    return $axios({
        url: '/user/user',
        method: 'post',
        data: { ...params }
    })
}

// 查询详情
const queryTeacherById = (id) => {
    return $axios({
        url: `/user/teacher/${id}`,
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
function enableOrDisableStatus (params) {
    return $axios({
        url: '/user/status',
        method: 'put',
        data: { ...params }
    })
}
//  -------------------------------user--------------------------------------------------------------------
// 查询列表接口
const getStudentPage = (params) => {
    return $axios({
        url: '/user/page',
        method: 'get',
        params
    })
}
// 删除接口
const deleteStudent = (ids) => {
    return $axios({
        url: '/user/user',
        method: 'delete',
        params: { ids }
    })
}

// 修改接口
const editStudent = (params) => {
    return $axios({
        url: '/user/student',
        method: 'put',
        data: { ...params }
    })
}

// 新增接口
const addStudent = (params) => {
    return $axios({
        url: '/user/user',
        method: 'post',
        data: { ...params }
    })
}

// 查询详情
const queryUserById = (id) => {
    return $axios({
        url: `/user/user/${id}`,
        method: 'get'
    })
}
//  /user/userCards/1 get
const getByIdWithCard = (id) => {
    return $axios({
        url: `/user/userCards/${id}`,
        method: 'get'
    })
}

// 分页查询全部card的信息
const getCardPage = (params) => {
    return $axios({
        url: '/card/page',
        method: 'get',
        params
    })
}
//  /user/saveByCardId/3  post   为该会员选择会员卡saveByIdWithCard
const saveByIdWithCard = (id,params) => {
    return $axios({
        url: `/user/user/saveByCardId/${id}`,
        method: 'post',
        data: { ...params }
    })
}

//  /user/userClasses/1 get  getByIdWithClasses
const getByIdWithClasses2 = (id) => {
    return $axios({
        url: `/user/userClasses/${id}`,
        method: 'get'
    })
}

// 修改---启用禁用接口
function enableOrDisableStudent (params) {
    return $axios({
        url: '/user/status',
        method: 'put',
        data: { ...params }
    })
}


