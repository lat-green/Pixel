import React, {useCallback} from "react";
import {Button, List, ListItem, ListItemButton, ListItemIcon, ListItemText, Skeleton} from "@mui/material";
import {useAsync, useOneRoom, useRecipientUser} from "./HookUtil";
import {getMeChats} from "../api/data/User";
import InboxIcon from '@mui/icons-material/MoveToInbox';
import MailIcon from '@mui/icons-material/Mail';
import {UserAvatar, UserIdInfo, UserName} from "./user/User";
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';

export function Contacts({onClick, onOpenCreateChat}: {
    onClick: (event: number) => void,
    onOpenCreateChat: () => void
}) {
    const rooms = useAsync(getMeChats, [])

    if (!rooms)
        return (<Skeleton/>)

    return (
        <>
            <List>
                {
                    rooms.map(roomId =>
                        <ListItem key={roomId} disablePadding>
                            <ChatComponent roomId={roomId} onClick={onClick}/>
                        </ListItem>
                    )
                }
            </List>
            <Button sx={{
                position: 'absolute',
                bottom: '10px',
                right: '10px',
            }} onClick={onOpenCreateChat}>
                <AddCircleOutlineIcon style={{
                    width: '40px',
                    height: '40px',
                }}/>
            </Button>
        </>
    )
}

function ChatContactComponent({roomId, handleClick}: { roomId: number, handleClick: () => void }) {
    const recipientId = useRecipientUser(roomId)

    if (!recipientId)
        return (<Skeleton variant="text"/>)

    return (
        <ListItemButton onClick={handleClick}>
            <UserIdInfo id={recipientId}>
                <ListItemIcon>
                    <UserAvatar/>
                </ListItemIcon>
                <ListItemText primary={<UserName/>}/>
            </UserIdInfo>
        </ListItemButton>
    )
}

function ChatComponent({roomId, onClick}: { roomId: number, onClick: (event: number) => void }) {
    const room = useOneRoom(roomId)

    const handleClick = useCallback(() => {
        onClick(roomId)
    }, [roomId])

    if (!room)
        return (<Skeleton variant="text"/>)

    if (room.type === "contact")
        return (<ChatContactComponent roomId={roomId} handleClick={handleClick}/>)

    return (
        <>
            <ListItemButton onClick={handleClick}>
                <ListItemIcon>
                    {
                        room.type === "group"
                            ? <MailIcon/>
                            : <InboxIcon/>
                    }
                </ListItemIcon>
                <ListItemText primary={room.title}/>
            </ListItemButton>
        </>
    )
}