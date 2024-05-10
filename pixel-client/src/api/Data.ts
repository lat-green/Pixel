import {AUTH_API_URI, CHAT_URI, ffetch, sfetch} from "./DataUtil";

export interface User {
    id: number
    username: string
}

export interface MessageCreateRequest {
    content: string
}

export const USER = {
    id: -1,
    username: "Anonymous"
} as User

export interface Chat {
    user: User
}

export async function getMeUser(): Promise<User> {
    return sfetch(`${AUTH_API_URI}/users/me`).then(resp => resp.json());
}

export async function getOneUser(id: number): Promise<User> {
    return ffetch(`${AUTH_API_URI}/users/${id}`).then(resp => resp.json());
}

export async function getAllUsers(): Promise<User[]> {
    return ffetch(`${AUTH_API_URI}/users`).then(resp => resp.json());
}

export async function findChatMessages(senderId: number, recipientId: number) {
    return sfetch(`${CHAT_URI}/messages/${senderId}/${recipientId}`).then(resp => resp.json());
}

export async function findChatMessage(messageId: number) {
    return sfetch(`${CHAT_URI}/messages/${messageId}`).then(resp => resp.json());
}

export async function sendChatMessage(recipientId: number, request: MessageCreateRequest) {
    return sfetch(`${CHAT_URI}/chats/${recipientId}/messages`, {
        method: 'POST',
        body: JSON.stringify(request),
    }).then(resp => resp.json());
}