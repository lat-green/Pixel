'use server'

import {AUTH_URI, SERVER_IP} from "./DataUtil";

interface Tokens {
    access_token: string,
    refresh_token: string,
    token_type: string,
}

export async function getTokens(code: string): Promise<Tokens> {

    return fetch(`${AUTH_URI}/oauth2/token?grant_type=authorization_code&code=${code}&redirect_uri=http://${SERVER_IP}:3000/auth/code`, {
        method: 'POST',
        headers: {
            'Authorization': "Basic dGVzdC1jbGllbnQ6dGVzdC1jbGllbnQ="
        }
    }).then(resp => resp.json());
}