export const API_URI = 'http://localhost:7777/api'

export async function ffetch(path, init) {
    const resp = await fetch(`${API_URI}${path}`, init)
    if (resp.ok)
        return resp
    if (resp.status === 401 || resp.status === 403) {
        window.location = '/auth/signin'
    }
    return resp
}

export async function sfetch(path, init) {
    if (!init)
        init = {}
    if (!init.headers)
        init.headers = {}
    if (!init.headers.Authorization) {
        const token = sessionStorage.getItem('access_token')
        if (token)
            init.headers.Authorization = `Bearer ${token}`
    }
    return ffetch(path, init)
}
