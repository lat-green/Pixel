import {DependencyList, useEffect, useState} from "react";
import {getOneRoom, getRoomUsers} from "../api/data/Room";
import {getMeUser, getOneUser} from "../api/data/User";
import {getOneMessage} from "../api/data/Message";

export function useFetch<T>(func: () => Promise<T>, deps?: DependencyList): T | undefined {
    const [result, setResult] = useState<T>()

    useEffect(() => {
        func().then(result => {
            setResult(result)
        })
    }, deps);

    return result
}

export function useOneRoom(roomId: number) {
    return useFetch(() => getOneRoom(roomId), [roomId])
}

export function useOneUser(userId: number) {
    return useFetch(() => getOneUser(userId), [userId])
}

export function useOneMessage(messageId: number) {
    return useFetch(() => getOneMessage(messageId), [messageId])
}

export function useRoomUsers(chatId: number) {
    return useFetch(() => getRoomUsers(chatId), [chatId])
}

export function useMeUser() {
    return useFetch(getMeUser, [])
}


