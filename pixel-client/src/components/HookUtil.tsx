import {DependencyList, useEffect, useMemo, useState} from "react";
import {getOneRoom, getRoomUsers, Room} from "../api/data/Room";
import {getMeUser, getOneUser} from "../api/data/User";
import {getOneMessage} from "../api/data/Message";

export function useAsync<T>(func: () => Promise<T>, deps?: DependencyList): T | undefined {
    const [result, setResult] = useState<T>()

    useEffect(() => {
        func().then(result => {
            setResult(result)
        })
    }, deps);

    return result
}

export function useOneRoom(roomId: number) {
    return useAsync(() => getOneRoom(roomId), [roomId])
}

export function useOneUser(userId: number) {
    return useAsync(() => getOneUser(userId), [userId])
}

export function useOneMessage(messageId: number) {
    return useAsync(() => getOneMessage(messageId), [messageId])
}

export function useRoomUsers(chatId: number) {
    return useAsync(() => getRoomUsers(chatId), [chatId])
}

export function useMeUser() {
    return useAsync(getMeUser, [])
}

export function useRecipientUser(roomId: number) {
    const me = useMeUser()
    const users = useRoomUsers(roomId)

    if (!me)
        return me
    if (!users)
        return users

    const notMe = users.filter(x => x.user != me.id)

    if (notMe.length === 0)
        return undefined

    return notMe[0].user
}

export function useChatTitle(chat: Room | undefined) {
    const me = useMeUser()
    if (!chat || !me)
        return undefined
    if (chat.type === "contact")
        return chat.title.replace(me.username, '').trim()
    return chat.title
}

export function useMapCallback<T, V, E>(value: V, func: (e: V) => ((e: E) => T)) {
    return useMemo(() => func(value), [value])
}

export function useMap<V, R>(value: V, func: (e: V) => R) {
    return useMemo(() => func(value), [value])
}
