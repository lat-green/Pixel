import * as React from 'react';
import {useCallback, useState} from 'react';
import {Box, Button, Drawer, Toolbar} from "@mui/material";

import './Home.css'
import {Chat} from "./Chat";
import {Contacts} from "../Contacts";
import Typography from "@mui/material/Typography";
import AppBar from "@mui/material/AppBar";
import {UserMeInfo, UserName} from "../user/User";

const drawerWidth = 240;

export default function Home() {
    const [chatId, setChatId] = useState<number | undefined>(undefined)
    const choiceChat = useCallback((event: number) => {
        setChatId(event)
    }, [setChatId])
    return (
        <Box sx={{display: 'grid'}}>
            <AppBar sx={{zIndex: (theme) => theme.zIndex.drawer + 1}}>
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{flexGrow: 1}}>
                        Pixel
                    </Typography>
                    <UserMeInfo>
                        <Button color="inherit"><UserName/></Button>
                    </UserMeInfo>
                </Toolbar>
            </AppBar>
            <Box sx={{display: 'flex'}}>
                <Drawer
                    variant="permanent"
                    sx={{
                        width: drawerWidth,
                        flexShrink: 0,
                        [`& .MuiDrawer-paper`]: {width: drawerWidth, boxSizing: 'border-box'},
                    }}
                >
                    <Toolbar/>
                    <Box sx={{overflow: 'auto'}}>
                        <Contacts onClick={choiceChat}/>
                    </Box>
                </Drawer>
                <Box component="main" sx={{flexGrow: 1, p: 3, padding: 0}}>
                    <Toolbar/>
                    {chatId ? <Chat chatId={chatId}/> : null}
                </Box>
            </Box>
        </Box>
    );
}