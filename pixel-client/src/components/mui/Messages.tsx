import {useMap, useMeUser, useOneRoom} from "../HookUtil";
import * as React from "react";
import {Dispatch, SetStateAction, useEffect, useMemo, useState} from "react";
import {deleteOneMessage, getOneMessage, MessageInfo} from "../../api/data/Message";
import {Button, List, ListItem, ListItemAvatar, ListItemText, Skeleton} from "@mui/material";
import {UserAvatar, UserIdInfo, UserName} from "../user/User";
import {Room} from "../../api/data/Room";
import {CustomMenuContainer} from "../CustomMenu";
import {ContextMenu} from "../styles/styles";
import {useSubscription} from "react-stomp-hooks";
import HideIf from "../HideIf";


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

    useSubscription("/user/" + chat?.id + "/edit", (message) => {
        const {messageId} = JSON.parse(message.body)
        setMessages(currentArray => [
            ...currentArray
        ].filter(m => m.id != messageId))
        getOneMessage(messageId).then((message) => {
            addElement(message)
        })
    });

    return messages
}

interface Props {
    chatId: number,
    editMessageId: number | undefined,
    setEditMessageId: Dispatch<SetStateAction<number | undefined>>,
}


export function Messages({chatId, editMessageId, setEditMessageId}: Props) {
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
                                ? <ChatMeMessage message={message} editMessageId={editMessageId}
                                                 setEditMessageId={setEditMessageId}/>
                                : createMessageComponent(message)
                        }

                    </div>
                )
                messageComponents.push(component)
            }
        }
        messageComponents.reverse()
        return messageComponents;
    }, [messages, me, chat, editMessageId])

    return (
        <List sx={{
            flexGrow: 1,
            p: 3,
            height: '120vh',
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

function ChatContactMessage({message}: {
    message: MessageInfo,
}) {


    return (
        <ListItem>
            <ListItemText primary={(<MessageContent message={message}/>)}
                          secondary={getTimeFromDate(message.sendTime)}/>
        </ListItem>
    );
}

function ChatMeMessage({message, editMessageId, setEditMessageId}: {
    message: MessageInfo,
    editMessageId: number | undefined,
    setEditMessageId: Dispatch<SetStateAction<number | undefined>>,
}) {

    function editMessage() {
        if (editMessageId === message.id)
            setEditMessageId(undefined)
        else
            setEditMessageId(message.id)
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
                            <HideIf hide={message.type !== "text"}>
                                <Button variant="text" onClick={editMessage}>Edit</Button>
                            </HideIf>
                            <Button color='error' variant="text" onClick={deleteMessage}>Delete</Button>
                        </ContextMenu>
                    }>
                        <div
                            style={{
                                color: editMessageId == message.id ? 'blue' : 'black'
                            }}
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
    if (message.type === 'text') {
        if (message.content.startsWith('http://'))
            return (
                <a href={message.content}>
                    {message.content}
                </a>
            )
        return (
            <>
                {message.content}
            </>
        )
    }

    if (message.type === 'image')
        return (<img src={message.url} width='50%' height='50%'/>)

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