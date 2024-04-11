import React, {useState} from "react";
import {Button} from "@mui/material";
import {UserInfo, UserName, useUser} from "./user/User";
import {Link, useParams} from "react-router-dom";
import {useIdUser} from "./util";
import {User} from "../api/Data";

export interface MessageInfo {
    senderId: number,
    recipientId: number,
    senderName: string,
    recipientName: string,
    content: string,
    timestamp: Date,
}

export function useMessages(sender: User, recipient: User): MessageInfo[] | undefined {
    const [messages, setMessages] = useState<MessageInfo[]>();

    // findChatMessages(sender.id, recipient.id).then((msgs) =>
    //     console.log(msgs)
    // );

    return messages
}

export function RealChat() {
    const [text, setText] = useState('')

    const currentUser = useUser()

    const recipient = useIdUser(parseInt(useParams().id!!))

    const messages = useMessages(currentUser, recipient)

    function sendMessage(text: string) {
        setText("");
        console.log(`send ${text}`)
    }

    return (
        <div className="content">
            <ul>
                <li><Link to="/">Home</Link></li>
            </ul>
            <UserInfo user={recipient}>
                <UserName/>
            </UserInfo>
            <ul>
                {
                    messages
                        ? messages.map(message => (
                            <Message text={message.content} senderId={message.senderId}/>
                        ))
                        : null
                }
            </ul>
            <div className="message-input">
                <div className="wrap">
                    <input
                        name="user_input"
                        placeholder="Write your message..."
                        value={text}
                        onChange={(event) => setText(event.target.value)}
                        onKeyPress={(event) => {
                            if (event.key === "Enter") {
                                sendMessage(text);
                            }
                        }}
                    />

                    <Button
                        onClick={() => {
                            sendMessage(text);
                        }}
                    />
                </div>
            </div>
        </div>
    )
}

export function Message({senderId, text}: { senderId: number, text: string }) {
    const sender = useIdUser(senderId)

    return (
        <li>
            <UserInfo user={sender}>
                <UserName/>: {text}
            </UserInfo>
        </li>
    )
}
