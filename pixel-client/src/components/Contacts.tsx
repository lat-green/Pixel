import React, {useCallback} from "react";
import {List, ListItem, ListItemButton, ListItemIcon, ListItemText, Skeleton} from "@mui/material";
import {useFetch, useOneRoom} from "./HookUtil";
import {getMeChats} from "../api/data/User";
import {AccountBox} from "@mui/icons-material";
import InboxIcon from '@mui/icons-material/MoveToInbox';
import MailIcon from '@mui/icons-material/Mail';

export function Contacts({onClick}: { onClick: (event: number) => void }) {
    const rooms = useFetch(getMeChats, [])

    return (
        <List>
            {
                rooms
                    ?
                    rooms.map(roomId =>
                        <ListItem key={roomId} disablePadding>
                            <ChatComponent roomId={roomId} onClick={onClick}/>
                        </ListItem>
                    )
                    : <Skeleton/>
            }
        </List>
    )
}

function ChatComponent({roomId, onClick}: { roomId: number, onClick: (event: number) => void }) {
    const room = useOneRoom(roomId)

    const handleClick = useCallback(() => {
        onClick(roomId)
    }, [roomId])

    return (
        <>
            {
                room
                    ? <ListItemButton onClick={handleClick}>
                        <ListItemIcon>
                            {
                                room.type === "group"
                                    ? <MailIcon/>
                                    : room.type === "channel"
                                        ? <InboxIcon/>
                                        : <AccountBox/>
                            }
                        </ListItemIcon>
                        <ListItemText primary={room.title}/>
                    </ListItemButton>
                    : <Skeleton variant="text"/>
            }
        </>
    )
}