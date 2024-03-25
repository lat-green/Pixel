import {ffetch, sfetch} from "./DataUtil";

export interface User {
    id: number
    username: string
}

export const USER = {
    id: -1,
    username: "Anonymous"
} as User

export interface Chat {
    user: User
}

export async function getMe(): Promise<User> {
    return sfetch('/users/me').then(resp => resp.json());
}

export async function getOne(id: number): Promise<User> {
    return ffetch('/users/me').then(resp => resp.json());
}
