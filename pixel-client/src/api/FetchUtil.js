export const SERVER_IP = 'localhost'
// export const SERVER_IP = '192.168.0.138'
const SERVER_PROTOCOL = `http://${SERVER_IP}`

export const AUTH_URI = `${SERVER_PROTOCOL}:7777`
export const CHAT_URI = `${SERVER_PROTOCOL}:3032`
export const CLIENT_URI = `${SERVER_PROTOCOL}:3000`
export const AUTH_TOKEN_URI = `${SERVER_PROTOCOL}:3001`

export async function ffetch(path, init) {
    if (!init)
        init = {}
    if (!init.headers)
        init.headers = {}
    if ((init.method === 'POST' || init.method === 'PUT') && !init.headers["Content-Type"])
        init.headers["Content-Type"] = "application/json"
    return fetch(path, init).catch(e => {
        throw new Error(`${e.message} on fetch ${path}`)
    })
}

export async function sfetch(path, init) {
    if (!init)
        init = {}
    if (!init.headers)
        init.headers = {}
    if (!init.headers.Authorization) {
        const token = localStorage.getItem('access_token')
        if (token)
            init.headers.Authorization = `Bearer ${token}`
        else {
            window.location.href = '/auth/signin'
            return Promise.reject(`have not access token`)
        }
    }
    const resp = await ffetch(path, init)
    if (resp.ok)
        return resp
    if (resp.status === 401 || resp.status === 403) {
        window.location.href = '/auth/signin'
        return Promise.reject(`resp.status === ${resp.status}`)
    }
    return resp
}
