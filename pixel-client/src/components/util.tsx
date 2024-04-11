import {useEffect, useState} from "react";
import {getOneUser, User} from "../api/Data";


export function useIdUser(id: number) {
    if (id === undefined)
        throw new Error(`id === ${id}`)
    const [user, setUser] = useState<User | undefined>(undefined)

    useEffect(() => {
        getOneUser(id).then((user) => {
            setUser(user)
        })
    }, [id]);

    return user ?? {
        id: id,
        username: ""
    };
}


