import React, {PropsWithChildren, useContext} from "react";
import {Avatar, Skeleton} from "@mui/material";
import {User} from "../../api/data/User";
import {useMeUser, useOneUser} from "../HookUtil";

export const UserContext = React.createContext<User | undefined>(undefined)

export function UserInfo({user, children}: PropsWithChildren<{ user: User }>) {
    return (
        <UserContext.Provider value={user}>
            {children}
        </UserContext.Provider>
    )
}

export function UserName({}) {
    const user = useContext(UserContext)
    if (user)
        return (<>{user.username}</>)
    return (<Skeleton/>)
}

export function useUser() {
    return useContext(UserContext)!!
}

export function UserIdInfo({id, children}: PropsWithChildren<{ id: number }>) {
    const user = useOneUser(id)

    return (
        <>
            {
                user ?
                    <UserInfo user={user}>
                        {children}
                    </UserInfo>
                    : null
            }
        </>
    )
}

export function UserMeInfo({children}: PropsWithChildren) {
    const user = useMeUser()

    return (
        <>
            {
                user ?
                    <UserInfo user={user}>
                        {children}
                    </UserInfo>
                    : null
            }
        </>
    )
}


export function UserAvatar({}) {
    const user = useContext(UserContext)
    if (user) {
        const name = user.username
        return (
            <Avatar
                src={user.avatar}
                alt={name}
                sx={{
                    width: 40,
                    height: 40
                }}
            >
                {getFirstLitter(name)}
            </Avatar>
        )
    }
    return (<Skeleton variant="circular" width={40} height={40}/>)
}

function getFirstLitter(name: string) {
    const words = name.split(' ', 2);
    let result = ''
    for (const word of words)
        result += word[0]
    return result
}