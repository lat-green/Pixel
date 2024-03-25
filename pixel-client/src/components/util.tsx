import {useEffect, useState} from "react";
import {getOne, USER, User} from "../api/Data";


export function useUser(id: number) {
    const [user, setUser] = useState<User | undefined>(undefined)

    useEffect(() => {
        getOne(id).then((user) => {
            setUser(user)
        })
    }, [id]);

    return user ?? USER;
}


