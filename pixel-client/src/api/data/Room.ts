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
    user: number,
    type: UserRoleAttachmentType
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
    return sfetch(`${CHAT_URI}/rooms/${roomId}/users`).then(resp => resp.json());
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
