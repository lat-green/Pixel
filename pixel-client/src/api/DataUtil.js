export const SERVER_IP = '192.168.0.138'
// export const SERVER_IP = new URL(window.location.origin).hostname
const SERVER_PROTOCOL = `http://${SERVER_IP}`

export const AUTH_URI = `${SERVER_PROTOCOL}:7777`
export const CHAT_URI = `${SERVER_PROTOCOL}:3032`

export const AUTH_API_URI = `${AUTH_URI}/api`

export async function ffetch(path, init) {
    if (!init)
        init = {}
    if (!init.headers)
        init.headers = {}
    if (!init.headers["Content-Type"])
        init.headers["Content-Type"] = "application/json"
    const resp = await fetch(path, init).catch(e => {
        throw new Error(`${e.message} on fetch ${path}`)
    })
    if (resp.ok)
        return resp
    if (resp.status === 401) {
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
