import React, {PropsWithChildren, useContext} from "react";
import {Skeleton} from "@mui/material";
import {Room} from "../../api/data/Room";
import {useOneRoom} from "../HookUtil";

export const RoomContext = React.createContext<Room | undefined>(undefined)

export function RoomInfo({room, children}: PropsWithChildren<{ room: Room }>) {
    return (
        <RoomContext.Provider value={room}>
            {children}
        </RoomContext.Provider>
    )
}

export function RoomIdInfo({id, children}: PropsWithChildren<{ id: number }>) {
    const room = useOneRoom(id)

    return (
        <>
            {
                room ?
                    <RoomInfo room={room}>
                        {children}
                    </RoomInfo>
                    : null
            }
        </>
    )
}

export function RoomTitle({}) {
    const room = useContext(RoomContext)
    if (room)
        return (<>{room.title}</>)
    return (<Skeleton/>)
}