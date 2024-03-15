import {Chat, User} from "../api/Data";

export function Chats() {
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
        <>
            {
                chats.map(({user}) => ChatComponent({user}))
            }
        </>
    )
}

function ChatComponent({user}: { user: User }) {
    return (
        <>
            {user.username}
        </>
    )
}