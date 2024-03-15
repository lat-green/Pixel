
import React, { PropsWithChildren, ReactNode, useContext, useEffect, useState } from "react";
import { User, getMe, getOne } from "../../api/Data";

export const UserContext = React.createContext<User | undefined>(undefined)

export function UserInfo({ user, children }: PropsWithChildren<{ user: User }>) {
    return (
        <UserContext.Provider value={user}>
            {children}
        </UserContext.Provider>
    )
}
export function UserIdInfo({ id, children }: PropsWithChildren<{ id: number }>) {
    const [user, setUser] = useState<User>()

    useEffect(() => {
        getOne(id).then((user) => {
            setUser(user)
        })
    }, [id])

    return (
        <>
        {
            user !== null && user !== undefined ?
            <UserInfo user={user}>
                {children}
            </UserInfo>
            : null
        }
        </>
    )
}

export function UserMeInfo({ children }: PropsWithChildren) {
    const [user, setUser] = useState<User>()

    useEffect(() => {
        getMe().then((user) => {
            setUser(user)
        })
    }, [])

    return (
        <>
        {
            user !== null && user !== undefined ?
            <UserInfo user={user}>
                {children}
            </UserInfo>
            : null
        }
        </>
    )
}

export function UserName({}) {
    const user = useContext(UserContext)!!
    return (<>{user.username}</>)
}

export function AnonymousOnly({children}: PropsWithChildren) {
    const user = useContext(UserContext)!!
    return (
        <>
        {
            user.id === -1
            ? <> {children} </>
            : null
        }
        </>
    )
}

export function AuthorizationOnly({children}: PropsWithChildren) {
    const user = useContext(UserContext)!!
    return (
        <>
        {
            user.id !== -1
            ? <> {children} </>
            : null
        }
        </>
    )
}
