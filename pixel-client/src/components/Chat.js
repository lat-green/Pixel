import React, {useCallback, useEffect, useState} from "react";
import {Button} from "@mui/material";
import {UserInfo, UserName, useUser} from "./user/User";
import {Link, useParams} from "react-router-dom";
import {useIdUser} from "./util";
import {CHAT_URI} from "../api/DataUtil";
import {Message} from "./NewChat";
import SockJS from "sockjs-client";
import {Stomp} from "@stomp/stompjs";
import {findChatMessage, findChatMessages, sendChatMessage} from "../api/Data";

let stompClient = Stomp.over(() => new SockJS(`${CHAT_URI}/ws`));

const Chat = (props) => {
    const [text, setText] = useState('')

    const currentUser = useUser()

    const recipient = useIdUser(parseInt(useParams().id))

    const [messages, setMessages] = useState([])

    const addLocalMessage = useCallback((msg) => {
        setMessages(messages => [
            ...messages,
            msg
        ]);
    }, [])

    useEffect(() => {
        findChatMessages(recipient.id, currentUser.id)
            .then(msgs => {
                console.log(msgs)
            })
        //.then(setMessages)
    }, [currentUser.id, recipient.id]);

    const onError = (err) => {
        console.log(`error ${err}`);
    };

    useEffect(() => {
        stompClient.connect({}, () => {
            stompClient.subscribe(
                "/user/" + currentUser.id + "/queue/messages",
                (msg) => {
                    const notification = JSON.parse(msg.body);
                    if (recipient.id === notification.senderId) {
                        findChatMessage(notification.id).then(addLocalMessage);
                    }
                }
            )
        }, onError);
    }, [addLocalMessage, currentUser.id, recipient.id]);

    const sendMessage = (msg) => {
        if (msg.trim() !== "") {
            // stompClient.send("/app/chat", {}, JSON.stringify(message));

            sendChatMessage(recipient.id, {
                content: msg,
            }).then(message => {
                addLocalMessage(message)
            })

            setText("")
        }
    };

    console.log("messages: ", messages)

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

export default Chat;
