import React, {useContext, useState} from "react";
import {Button} from "@mui/material";
import {UserContext, UserIdInfo, UserName} from "./user/User";
import {Link, useParams} from "react-router-dom";

export function Chat() {
    const [text, setText] = useState('')

    const currentUser = useContext(UserContext)!!

    const id = parseInt(useParams().id!!)

    function sendMessage(text: string) {
        setText("");
        console.log(`send ${text}`)
    }

    return (
        <div className="content">
            <ul>
                <li><Link to="/">Home</Link></li>
            </ul>
            <UserIdInfo id={id}>
                <UserName/>
            </UserIdInfo>
            <ul>
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
