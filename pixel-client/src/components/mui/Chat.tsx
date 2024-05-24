import * as React from 'react';
import {useEffect, useState} from 'react';
import Box from '@mui/material/Box';
import AppBar from '@mui/material/AppBar';
import Typography from '@mui/material/Typography';
import {Drawer, IconButton, List, ListItem, ListItemAvatar, ListItemText, Skeleton, Toolbar} from "@mui/material";
import {useMeUser, useOneRoom, useRoomUsers} from "../HookUtil";
import {UserAvatar, UserIdInfo, UserName} from "../user/User";
import {UserRoleAttachment} from "../../api/data/Room";
import './Chat.css'
import MenuIcon from '@mui/icons-material/Menu';
import {Send} from '@mui/icons-material';
import {createTextMessage, getOneMessage, MessageInfo} from "../../api/data/Message";

export function Chat({chatId}: { chatId: number }) {
    const [text, setText] = useState('')
    const chat = useOneRoom(chatId)
    const me = useMeUser()
    const [open, setOpen] = React.useState(false);

    const handleDrawerOpen = () => {
        setOpen(true);
    };

    const handleDrawerClose = () => {
        setOpen(false);
    };

    const [messages, setMessages] = useState<MessageInfo[]>([])

    const addLocalMessage = (msg: MessageInfo) => {
        setMessages(messages => [
            ...messages,
            msg
        ]);
    }

    useEffect(() => {
        if (chat)
            for (const messageId of chat.messages) {
                getOneMessage(messageId).then((message) => {
                    addLocalMessage(message)
                })
            }
    }, [chat]);

    useEffect(() => {
        setMessages(messages => [...messages]
            .sort((a: MessageInfo, b: MessageInfo) => b.sendTime.getTime() - a.sendTime.getTime()))
    }, [messages.length]);

    if (!chat)
        return (<Skeleton/>)

    console.log('messages', messages)

    const sendMessage = (msg: string) => {
        if (msg.trim() !== "") {
            // stompClient.send("/app/chat", {}, JSON.stringify(message));

            if (chat)
                createTextMessage(chat.id, {
                    content: msg,
                }).then(message => {
                    addLocalMessage(message)
                })

            setText("")
        }
    };

    return (
        <Box sx={{display: 'grid'}}>
            <AppBar position="sticky" sx={{top: '64px', zIndex: (theme) => theme.zIndex.drawer - 1}}>
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{flexGrow: 1}}>
                        {chat.title}
                    </Typography>
                    <IconButton
                        color="inherit"
                        aria-label="open drawer"
                        onClick={handleDrawerOpen}
                        edge="start"
                        sx={{mr: 2, ...(open && {display: 'none'})}}
                    >
                        <MenuIcon/>
                    </IconButton>
                </Toolbar>
            </AppBar>
            <Drawer
                anchor="right"
                open={open}
                onClose={handleDrawerClose}
            >
                <Box sx={{width: 250}} role="presentation" onClick={handleDrawerClose}>
                    <Toolbar/>
                    <Box sx={{overflow: 'auto'}}>
                        <ChatUsers chatId={chatId}/>
                    </Box>
                </Box>
            </Drawer>
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
                    me
                        ?
                        messages.map(message =>
                            <div key={message.id}
                                 style={{
                                     display: 'flex'
                                 }}>
                                <ChatMessage message={message} currentUserId={me.id}/>
                            </div>
                        )
                        : null
                }
            </List>

            <AppBar position="sticky" color='inherit'
                    sx={{bottom: '0px', zIndex: (theme) => theme.zIndex.drawer - 1}}>
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{
                        flexGrow: 1,
                    }}>
                        <textarea
                            name="user_input"
                            placeholder="Write your message..."
                            value={text}
                            onChange={(event) => setText(event.target.value)}
                            onKeyPress={(event) => {
                                if (event.key === 'Enter')
                                    sendMessage(text);
                            }}
                            style={{
                                border: 'none',
                                width: '100%',
                            }}
                        />
                    </Typography>
                    <IconButton
                        color="primary"
                        aria-label="send message"
                        onClick={() => {
                            sendMessage(text);
                        }}
                        edge="start"
                        sx={{
                            ml: 2,
                        }}
                    >
                        <Send/>
                    </IconButton>
                </Toolbar>
            </AppBar>
        </Box>
    );
}

function getRole(attachment: UserRoleAttachment): string {
    if ('role' in attachment)
        return attachment.role
    return ""
}

function ChatUsers({
                       chatId
                   }: {
    chatId: number
}) {
    const attachments = useRoomUsers(chatId)

    return (
        <List>
            {
                attachments
                    ?
                    attachments.map(attachment =>
                        <ListItem key={attachment.user}>
                            <UserIdInfo id={attachment.user}>
                                <ListItemAvatar>
                                    <UserAvatar/>
                                </ListItemAvatar>
                                <ListItemText primary={<UserName/>} secondary={getRole(attachment)}/>
                            </UserIdInfo>
                        </ListItem>
                    )
                    : <Skeleton/>
            }
        </List>
    )
}

function ChatMessage({message, currentUserId}: { message: MessageInfo, currentUserId: number }) {
    if (currentUserId === message.user)
        return (
            <ListItem style={{
                textAlign: 'right'
            }}>
                <UserIdInfo id={message.user}>
                    <ListItemText primary={message.content}
                                  secondary={date_TO_String(message.sendTime)}/>
                </UserIdInfo>
            </ListItem>
        );

    return (
        <ListItem>
            <UserIdInfo id={message.user}>
                <ListItemAvatar>
                    <UserAvatar/>
                </ListItemAvatar>
                <ListItemText primary={message.content}
                              secondary={<><UserName/> {date_TO_String(message.sendTime)}</>}/>
            </UserIdInfo>
        </ListItem>
    );
}

function date_TO_String(date_Object: Date) {
    const h = date_Object.getHours()
    const m = date_Object.getMinutes()
    return `${h}:${m}`;
}