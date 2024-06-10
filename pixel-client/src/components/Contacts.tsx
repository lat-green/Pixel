import React, {useCallback} from "react";
import {Avatar, List, ListItem, ListItemButton, ListItemIcon, ListItemText, Skeleton} from "@mui/material";
import {useFetch, useOneRoom, useRecipientUser} from "./HookUtil";
import {getMeChats} from "../api/data/User";
import InboxIcon from '@mui/icons-material/MoveToInbox';
import MailIcon from '@mui/icons-material/Mail';
import {UserAvatar, UserIdInfo, UserName} from "./user/User";

export function Contacts({onClick}: { onClick: (event: number) => void }) {
    const rooms = useFetch(getMeChats, [])

    if (!rooms)
        return (<Skeleton/>)

    return (
        <List>
            {
                rooms.map(roomId =>
                    <ListItem key={roomId} disablePadding>
                        <ChatComponent roomId={roomId} onClick={onClick}/>
                    </ListItem>
                )
            }
        </List>
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