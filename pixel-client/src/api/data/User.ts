import {CHAT_URI, ffetch, sfetch} from "../FetchUtil";
import {toAttachmentEach} from "./Room";

export interface User {
    id: number,
    username: string,
    avatar: string
}

export interface UserReplaceRequest {
    username?: string,
    avatar?: string
}

export async function getMeUser(): Promise<User> {
    return sfetch(`${CHAT_URI}/users/me`).then(resp => resp.json());
}

export async function getMeChats(): Promise<number[]> {
    return sfetch(`${CHAT_URI}/users/me/chats`).then(resp => resp.json());
}

export async function getMeAttachments() {
    return sfetch(`${CHAT_URI}/users/me/attachments`).then(resp => resp.json()).then(toAttachmentEach);
}

export async function getOneUser(id: number): Promise<User> {
    return ffetch(`${CHAT_URI}/users/${id}`).then(resp => resp.json());
}

export async function replaceUserName(name: string): Promise<User> {
    return replaceUser({
        username: name
    })
}

export async function replaceUser(request: UserReplaceRequest): Promise<User> {
    return sfetch(`${CHAT_URI}/users`, {
        method: 'PUT',
        body: JSON.stringify(request),
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(resp => resp.json());
}