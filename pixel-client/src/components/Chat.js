import React, {useCallback, useEffect, useState} from "react";
import {Button} from "@mui/material";
import {useUser} from "./user/User";
import {Link, useParams} from "react-router-dom";
import SockJS from "sockjs-client";
import {Stomp} from "@stomp/stompjs";
import {RoomInfo, RoomTitle} from "./user/Room";
import {TextMessage} from "./NewChat";
import {CHAT_URI} from "../api/FetchUtil";
import {useOneRoom} from "./HookUtil";
import {createTextMessage, getOneMessage} from "../api/data/Message";

let stompClient = Stomp.over(() => new SockJS(`${CHAT_URI}/ws`));

const Chat = (props) => {
    const [text, setText] = useState('')

    const currentUser = useUser()

    const room = useOneRoom(parseInt(useParams().id))

    const [messages, setMessages] = useState([])

    const addLocalMessage = useCallback((msg) => {
        setMessages(messages => [
            ...messages,
            msg
        ]);
    }, [])

    useEffect(() => {
        if (room)
            for (const messageId of room.messages) {
                getOneMessage(messageId).then((message) => {
                    addLocalMessage(message)
                })
            }
    }, [room]);

    const onError = (err) => {
        console.log(`error ${err}`);
    };

    // useEffect(() => {
    //     stompClient.connect({}, () => {
    //         stompClient.subscribe(
    //             "/user/" + currentUser.id + "/queue/messages",
    //             (msg) => {
    //                 const notification = JSON.parse(msg.body);
    //                 if (room.id === notification.senderId) {
    //                     findChatMessage(notification.id).then(addLocalMessage);
    //                 }
    //             }
    //         )
    //     }, onError);
    // }, [addLocalMessage, currentUser.id, room.id]);

    const sendMessage = (msg) => {
        if (msg.trim() !== "") {
            // stompClient.send("/app/chat", {}, JSON.stringify(message));

            if (room)
                createTextMessage(room.id, {
                    content: msg,
                }).then(message => {
                    addLocalMessage(message)
                })

            setText("")
        }
    };

    return (
        <div className="content">
            <ul>
                <li><Link to="/">Home</Link></li>
            </ul>
            <RoomInfo room={room}>
                <RoomTitle/>
            </RoomInfo>
            <ul>
                {
                    messages
                        ? messages.map(message => (
                            <TextMessage text={message.content} senderId={message.user}/>
                        ))
                        : null
                }
            </ul>
            <div className="message-input">
                <div className="wrap">
                    <input
                        style={{
                            outlineWidth: 0
                        }}
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
