import {sfetch} from "./DataUtil";

export interface User {
    id: number
    username: string
}

export interface Chat {
    user: User
}

export async function getMe(): Promise<User> {
    return sfetch('/users/me').then(resp => resp.json());
}

export async function getOne(id: number): Promise<User> {
    throw new Error("getOne")
}
