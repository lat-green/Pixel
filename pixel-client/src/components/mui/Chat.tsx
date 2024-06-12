import * as React from 'react';
import {useState} from 'react';
import Box from '@mui/material/Box';
import AppBar from '@mui/material/AppBar';
import Typography from '@mui/material/Typography';
import {
    Button,
    Divider,
    Drawer,
    IconButton,
    List,
    ListItem,
    ListItemAvatar,
    ListItemText,
    Skeleton,
    Toolbar
} from "@mui/material";
import {useChatTitle, useOneRoom, useRoomUsers} from "../HookUtil";
import {UserAvatar, UserIdInfo, UserName} from "../user/User";
import {leaveRoom, UserChanelRoleAttachmentRole, UserRoleAttachment} from "../../api/data/Room";
import './Chat.css'
import MenuIcon from '@mui/icons-material/Menu';
import {Send} from '@mui/icons-material';
import {createTextMessage} from "../../api/data/Message";
import {Messages} from "./Messages";

export function Chat({chatId}: { chatId: number }) {
    const [text, setText] = useState('')
    const chat = useOneRoom(chatId)
    const chatTitle = useChatTitle(chat)
    const [open, setOpen] = React.useState(false);

    const handleDrawerOpen = () => {
        setOpen(true);
    };

    const handleDrawerClose = () => {
        setOpen(false);
    };

    if (!chat)
        return (<Skeleton/>)

    const sendMessage = (msg: string) => {
        if (msg.trim() !== "") {
            if (chat)
                createTextMessage(chat.id, {
                    content: msg,
                }).then(message => {
                })

            setText("")
        }
    };

    function handleExit() {
        leaveRoom(chatId).then(() => {
        })
    }

    return (
        <Box sx={{display: 'grid'}}>
            <AppBar position="sticky" sx={{top: '64px', zIndex: (theme) => theme.zIndex.drawer - 1}}>
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{flexGrow: 1}}>
                        {chatTitle}
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
                    <Divider/>
                    <Box sx={{overflow: 'auto'}}>
                        <ChatUsers chatId={chatId}/>
                    </Box>
                    <Divider/>
                    <Box sx={{p: 1, m: 1}}>
                        <Button color='warning' onClick={handleExit}>
                            Exit
                        </Button>
                    </Box>
                </Box>
            </Drawer>
            <Messages chatId={chatId}/>
            <AppBar position="sticky" color='inherit'
                    sx={{bottom: '0px', zIndex: (theme) => theme.zIndex.drawer - 1}}>
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{
                        flexGrow: 1,
                    }}>
                        <textarea
                            name="user_input"
                            placeholder="Напишите свое сообщение..."
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
        switch (attachment.role) {
            case UserChanelRoleAttachmentRole.PUBLIC_USER:
                return "USER"
            default:
                return attachment.role
        }
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