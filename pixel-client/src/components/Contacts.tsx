import React, {DependencyList, useEffect, useState} from "react";
import {getAllUsers, User} from "../api/Data";
import {Link} from "react-router-dom";
import {useUser} from "./user/User";

function useFetch<T>(func: () => Promise<T>, deps?: DependencyList): T | undefined {
    const [result, setResult] = useState<T>()

    useEffect(() => {
        func().then(result => {
            setResult(result)
        })
    }, deps);

    return result
}

export function Contacts() {
    const me = useUser()
    const chats = useFetch(getAllUsers, [])

    if (!chats)
        return (
            <>
                Loading ...
            </>
        )

    return (
        <ul>
            {
                chats.filter(user => user.id !== me.id).map((user) => (
                    ChatComponent({user})
                ))
            }
        </ul>
    )
}

function ChatComponent({user}: { user: User }) {
    return (
        <li>
            <Link to={`/chat/${user.id}`}>
                {user.username}
            </Link>
        </li>
    )
}