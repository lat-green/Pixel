import React, {PropsWithChildren, useContext, useEffect, useState} from "react";
import {getMeUser, getOneUser, User} from "../../api/Data";
import {Skeleton} from "@mui/material";

export const UserContext = React.createContext<User | undefined>(undefined)

export function UserInfo({user, children}: PropsWithChildren<{ user: User }>) {
    return (
        <UserContext.Provider value={user}>
            {children}
        </UserContext.Provider>
    )
}

export function useUser() {
    return useContext(UserContext)!!
}

export function UserIdInfo({id, children}: PropsWithChildren<{ id: number }>) {
    const [user, setUser] = useState<User>()

    useEffect(() => {
        getOneUser(id).then((user) => {
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

export function UserMeInfo({children}: PropsWithChildren) {
    const [user, setUser] = useState<User>()

    useEffect(() => {
        getMeUser().then((user) => {
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
    const user = useContext(UserContext)
    if (user)
        return (<>{user.username}</>)
    return (<Skeleton/>)
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
