import React, {useEffect, useState} from "react";
import {Button} from "@mui/material";
import {UserInfo, UserName, useUser} from "./user/User";
import {Link, useParams} from "react-router-dom";
import {useIdUser} from "./util";
import {CHAT_URI} from "../api/DataUtil";
import {Message} from "./NewChat";
import SockJS from "sockjs-client";
import {Stomp} from "@stomp/stompjs";
import {findChatMessage, findChatMessages} from "../api/Data";

let stompClient = null;

const Chat = (props) => {
    const [text, setText] = useState('')

    const currentUser = useUser()

    const recipient = useIdUser(parseInt(useParams().id))

    const [messages, setMessages] = useState([])

    const addLocalMessage = (msg) => {
        const newMessages = [...messages]
        newMessages.push(msg);
        setMessages(newMessages);
    }

    const onMessageReceived = (msg) => {
        const notification = JSON.parse(msg.body);

        if (recipient.id === notification.senderId) {
            findChatMessage(notification.id).then((message) => {
                addLocalMessage(message)
            });
        } else {
            console.log("Received a new message from " + notification.senderName);
        }
    }

    useEffect(() => {
        findChatMessages(recipient.id, currentUser.id).then((msgs) => setMessages(msgs))
        stompClient = Stomp.over(() => {
            return new SockJS(`${CHAT_URI}/ws`)
        });
    }, []);

    useEffect(() => {
        stompClient.connect({}, () => {
            stompClient.subscribe(
                "/user/" + currentUser.id + "/queue/messages",
                onMessageReceived
            )
        }, onError);
    });

    const onError = (err) => {
        console.log(`error ${err}`);
    };

    const sendMessage = (msg) => {
        if (msg.trim() !== "") {
            const message = {
                senderId: currentUser.id,
                recipientId: recipient.id,
                senderName: currentUser.username,
                recipientName: recipient.username,
                content: msg,
                timestamp: new Date(),
            };
            stompClient.send("/app/chat", {}, JSON.stringify(message));

            addLocalMessage(message)

            // setText("")
        }
    };

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