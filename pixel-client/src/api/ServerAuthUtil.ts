'use server'

export const AUTH_URL = 'http://localhost:7777'

interface Tokens {
    access_token: string,
    refresh_token: string,
    token_type: string,
}

export async function getTokens(code: string): Promise<Tokens> {

    return fetch(`${AUTH_URL}/oauth2/token?grant_type=authorization_code&code=${code}&redirect_uri=http://localhost:3000/auth/code`, {
        method: 'POST',
        headers: {
            'Authorization': "Basic dGVzdC1jbGllbnQ6dGVzdC1jbGllbnQ="
        }
    }).then(resp => resp.json());
}