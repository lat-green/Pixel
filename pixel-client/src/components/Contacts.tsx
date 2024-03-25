import React from "react";
import {Chat, User} from "../api/Data";
import {Link} from "react-router-dom";


export function Contacts() {
    let chats: Chat[] = []
    chats.push({
        user: {
            id: 45,
            username: "Nikita"
        }
    })
    chats.push({
        user: {
            id: 45,
            username: "Maksim"
        }
    })
    
    return (
        <ul>
            {
                chats.map(({user}) => (
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