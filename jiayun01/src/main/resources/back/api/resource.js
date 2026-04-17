const initUpload = (params) => {
    return $axios({
        url: '/resource/initUpload',
        method: 'post',
        data: { ...params }
    })
}

const uploadChunk = (formData) => {
    return $axios({
        url: '/resource/uploadChunk',
        method: 'post',
        data: formData,
        headers: { 'Content-Type': 'multipart/form-data' },
        timeout: 60000
    })
}

const checkChunk = (md5) => {
    return $axios({
        url: '/resource/checkChunk',
        method: 'get',
        params: { md5 }
    })
}

const mergeChunk = (md5) => {
    return $axios({
        url: '/resource/merge',
        method: 'post',
        params: { md5 }
    })
}

const getResourcePage = (params) => {
    return $axios({
        url: '/resource/page',
        method: 'get',
        params
    })
}

const deleteResource = (id) => {
    return $axios({
        url: '/resource',
        method: 'delete',
        params: { id }
    })
}