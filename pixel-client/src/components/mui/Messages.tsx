import {useMap, useMeUser, useOneRoom} from "../HookUtil";
import * as React from "react";
import {useEffect, useMemo, useState} from "react";
import {deleteOneMessage, getOneMessage, MessageInfo} from "../../api/data/Message";
import {Button, List, ListItem, ListItemAvatar, ListItemText, Skeleton} from "@mui/material";
import {UserAvatar, UserIdInfo, UserName} from "../user/User";
import {Room} from "../../api/data/Room";
import {CustomMenuContainer} from "../CustomMenu";
import {ContextMenu} from "../styles/styles";
import {useSubscription} from "react-stomp-hooks";


interface Props {
    chatId: number
}

export function useMessages(chat: Room | undefined) {
    const [messages, setMessages] = useState<MessageInfo[]>([])

    function addElement(element: MessageInfo) {
        setMessages(currentArray => [
            ...currentArray,
            element
        ].sort((a: MessageInfo, b: MessageInfo) => a.sendTime.getTime() - b.sendTime.getTime()))
    }

    useEffect(() => {
        setMessages([])
    }, [chat?.id]);

    useEffect(() => {
        if (chat)
            for (const messageId of chat.messages) {
                getOneMessage(messageId).then((message) => {
                    addElement(message)
                })
            }
    }, [chat?.id]);

    useSubscription("/user/" + chat?.id + "/create", (message) => {
        const {messageId} = JSON.parse(message.body)
        getOneMessage(messageId).then((message) => {
            addElement(message)
        })
    });

    useSubscription("/user/" + chat?.id + "/delete", (message) => {
        const {messageId} = JSON.parse(message.body)
        setMessages(currentArray => [
            ...currentArray
        ].filter(m => m.id != messageId))
    });

    return messages
}

export function Messages({chatId}: Props) {
    const chat = useOneRoom(chatId)
    const me = useMeUser()
    const messages = useMessages(chat)

    const createMessageComponent = useMap(chat?.type, (type) => {
        if (type === "contact")
            return (message: MessageInfo) => (<ChatContactMessage message={message}/>)
        return (message: MessageInfo) => (<ChatMessage message={message}/>)
    })

    const messageComponents = useMemo(() => {
        const messageComponents: React.JSX.Element[] = []

        if (messages.length > 0 && me) {
            let lastDate = -1
            for (const message of messages) {
                const date = getAbsoluteDay(message.sendTime)
                if (date != lastDate) {
                    lastDate = date
                    const component = (
                        <CenterMarker key={-message.id} text={getDayFromDate(message.sendTime)}/>
                    )
                    messageComponents.push(component)
                }
                const component = (
                    <div key={message.id}
                         style={{
                             display: 'flex'
                         }}>
                        {
                            message.user === me.id
                                ? <ChatMeMessage message={message}/>
                                : createMessageComponent(message)
                        }

                    </div>
                )
                messageComponents.push(component)
            }
        }
        messageComponents.reverse()
        return messageComponents;
    }, [messages, me, chat])

    return (
        <List component="main" sx={{
            flexGrow: 1,
            p: 3,
            height: '100vh',
            overflow: 'scroll',
        }}
              style={{
                  display: 'flex',
                  flexDirection: 'column-reverse'
              }}>
            {
                messageComponents
            }
        </List>
    )
}

function CenterMarker({text}: { text: string | number }) {
    return (
        <ListItem style={{
            color: 'white',
            textAlign: 'center',
            background: 'gray',
            alignSelf: 'center',
            width: '100px',
            height: '20px',
            borderRadius: '100px',
            position: 'sticky',
            fontSize: 8,
        }}>
            <ListItemText primary={text}/>
        </ListItem>
    );
}

function ChatMessage({message}: { message: MessageInfo }) {
    return (
        <ListItem>
            <UserIdInfo id={message.user}>
                <ListItemAvatar>
                    <UserAvatar/>
                </ListItemAvatar>
                <ListItemText primary={(<MessageContent message={message}/>)}
                              secondary={<><UserName/> {getTimeFromDate(message.sendTime)}</>}/>
            </UserIdInfo>
        </ListItem>
    );
}

function ChatContactMessage({message}: { message: MessageInfo }) {
    return (
        <ListItem>
            <ListItemText primary={(<MessageContent message={message}/>)}
                          secondary={getTimeFromDate(message.sendTime)}/>
        </ListItem>
    );
}

function ChatMeMessage({message}: { message: MessageInfo }) {

    function editMessage() {
        console.log("edit")
    }

    function deleteMessage() {
        deleteOneMessage(message.id)
    }

    return (
        <ListItem
        >
            <ListItemText
                style={{
                    textAlign: 'right'
                }}
                primary={
                    <CustomMenuContainer contextMenu={
                        <ContextMenu
                            style={{
                                textAlign: 'right'
                            }}>
                            {/*<Button variant="text" onClick={editMessage}>Edit</Button>*/}
                            <Button color='error' variant="text" onClick={deleteMessage}>Delete</Button>
                        </ContextMenu>
                    }>
                        <div
                        >
                            <MessageContent message={message}/>
                        </div>
                    </CustomMenuContainer>
                }
                secondary={getTimeFromDate(message.sendTime)}/>
        </ListItem>
    );

}

function MessageContent({message}: { message: MessageInfo }) {
    if (message.type === 'text')
        return (
            <>
                {message.content}
            </>
        )

    if (message.type === 'image')
        return (<img src={message.url} width='30%' height='30%'/>)

    return (<Skeleton/>)
}

function getAbsoluteDay(date: Date) {
    return Math.ceil(date.getTime() / 86400000)
}

function getDayFromDate(date: Date) {
    const d = date.getUTCDate()
    const m = date.getUTCMonth() + 1
    if (m < 10)
        return `${d}.0${m}`;
    return `${d}.${m}`;
}

function getTimeFromDate(date: Date) {
    const h = date.getHours()
    const m = date.getMinutes()
    if (m < 10)
        return `${h}:0${m}`;
    return `${h}:${m}`;
}