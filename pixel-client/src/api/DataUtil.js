export const SERVER_IP = "192.168.121.231"
const SERVER_PROTOCOL = `http://${SERVER_IP}`

export const AUTH_URI = `${SERVER_PROTOCOL}:7777`
export const CHAT_URI = `${SERVER_PROTOCOL}:3032`

export const AUTH_API_URI = `${AUTH_URI}/api`

export async function ffetch(path, init) {
    const resp = await fetch(`${path}`, init)
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
    if (!init.headers["Content-Type"])
        init.headers["Content-Type"] = "application/json"
    if (!init.headers.Authorization) {
        const token = sessionStorage.getItem('access_token')
        if (token)
            init.headers.Authorization = `Bearer ${token}`
    }
    return ffetch(path, init)
}
