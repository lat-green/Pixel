import {CHAT_URI, sfetch} from "../FetchUtil";

export type RoomType = "group" | "channel" | "contact"

interface RoomRaw {
    id: number,
    title: string,
    messages: number[]
    type: RoomType,
}

export interface GroupRoom extends RoomRaw {
    type: "group"
}

export interface ChannelRoom extends RoomRaw {
    type: "channel"
}

export interface ContactRoom extends RoomRaw {
    type: "contact"
}

export type Room = GroupRoom | ChannelRoom | ContactRoom

export type UserRoleAttachmentType = 'group' | 'channel' | 'contact'

interface UserRoleAttachmentRaw {
    id: number,
    user: number,
    chat: number,

    type: UserRoleAttachmentType,

    lastRead: Date
}

export enum UserGroupRoleAttachmentRole {
    ADMIN = "ADMIN",
    USER = "USER",
}

export interface UserGroupRoleAttachment extends UserRoleAttachmentRaw {
    role: UserGroupRoleAttachmentRole,
    type: 'group'
}

export enum UserChanelRoleAttachmentRole {
    ADMIN = "ADMIN",
    AUTHOR = "AUTHOR",
    PUBLIC_USER = "PUBLIC_USER",
    PRIVATE_USER = "PRIVATE_USER",
}

export interface UserChanelRoleAttachment extends UserRoleAttachmentRaw {
    role: UserChanelRoleAttachmentRole,
    type: 'channel'
}

export interface UserContactRoleAttachment extends UserRoleAttachmentRaw {
    type: 'contact'
}

export type UserRoleAttachment = UserGroupRoleAttachment | UserChanelRoleAttachment | UserContactRoleAttachment

export interface GroupRoomCreateRequest {
    title: string;
}

export async function getOneRoom(roomId: number): Promise<Room> {
    return sfetch(`${CHAT_URI}/rooms/${roomId}`).then(resp => resp.json());
}

export async function getRoomUsers(roomId: number): Promise<UserRoleAttachment[]> {
    return sfetch(`${CHAT_URI}/rooms/${roomId}/users`).then(resp => resp.json()).then(toAttachmentEach);
}

export async function getAllRooms(): Promise<Room[]> {
    return sfetch(`${CHAT_URI}/rooms`).then(resp => resp.json());
}

export async function createGroupRoom(request: GroupRoomCreateRequest): Promise<Room> {
    return sfetch(`${CHAT_URI}/rooms/group`, {
        method: 'POST',
        body: JSON.stringify(request),
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(resp => resp.json());
}

export async function joinRoom(chatId: number) {
    return sfetch(`${CHAT_URI}/rooms/${chatId}/join`);
}

export async function leaveRoom(chatId: number) {
    return sfetch(`${CHAT_URI}/rooms/${chatId}/leave`);
}

export async function updateLastRead(chatId: number) {
    return sfetch(`${CHAT_URI}/rooms/${chatId}/read`).then(resp => resp.json()).then(toAttachment);
}


export function toAttachment(message: any): UserRoleAttachment {
    const copy = Object.assign({}, message);
    copy.lastRead = new Date(copy.lastRead)
    return copy as UserRoleAttachment
}

export function toAttachmentEach(attachments: any[]): UserRoleAttachment[] {
    let result: UserRoleAttachment[] = []
    for (const attachment of attachments)
        result.push(toAttachment(attachment))
    return result
}