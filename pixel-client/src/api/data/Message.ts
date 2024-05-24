import {CHAT_URI, sfetch} from "../FetchUtil";


export type MessageInfoType = "text"

interface MessageInfoRaw {
    id: number,

    sendTime: Date,
    user: number,
    room: number,

    type: MessageInfoType
}

export interface TextMessageInfo extends MessageInfoRaw {
    content: string,
    type: "text",
}

export type MessageInfo = TextMessageInfo

export interface TextMessageCreateRequest {
    content: string,
}

export async function getOneMessage(messageId: number): Promise<MessageInfo> {
    return sfetch(`${CHAT_URI}/messages/${messageId}`).then(resp => resp.json()).then(toMessage);
}

export async function createTextMessage(roomId: number, request: TextMessageCreateRequest): Promise<MessageInfo> {
    return sfetch(`${CHAT_URI}/rooms/${roomId}/messages/text`, {
        method: 'POST',
        body: JSON.stringify(request),
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(resp => resp.json()).then(toMessage);
}

export async function getChatMessages(roomId: number): Promise<MessageInfo[]> {
    return sfetch(`${CHAT_URI}/rooms/${roomId}/messages`).then(resp => resp.json()).then(toMessageEach);
}

export function toMessage(message: any): MessageInfo {
    const copy = Object.assign({}, message);
    copy.sendTime = new Date(copy.sendTime)
    return copy as MessageInfo
}

export function toMessageEach(messages: any[]): MessageInfo[] {
    let result: MessageInfo[] = []
    for (const message of messages)
        result.push(message)
    return result
}