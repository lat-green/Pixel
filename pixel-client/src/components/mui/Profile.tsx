import * as React from 'react';
import {useContext, useState} from 'react';
import {styled} from '@mui/material/styles';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import Typography from '@mui/material/Typography';
import {UserAvatar, UserMeInfo, UserName} from "../user/User";
import {Button, ListItem, ListItemAvatar} from '@mui/material';
import {AuthContext} from "react-oauth2-code-pkce";
import {DialogContentProps, PromptInfo, useSmartPrompt} from "../prompt/SmartPrompt";
import {replaceUserName} from "../../api/data/User";

interface Props {
    open: boolean,
    handleClose: () => void
}

const BootstrapDialog = styled(Dialog)(({theme}) => ({
    '& .MuiDialogContent-root': {
        padding: theme.spacing(2),
    },
    '& .MuiDialogActions-root': {
        padding: theme.spacing(1),
    },
}));

function MainProfile({setPromptName, handleClose}: DialogContentProps) {
    const context = useContext(AuthContext)

    function handleChangeName() {
        setPromptName('change-name')
    }

    function handleChangeAvatar() {
        setPromptName('change-avatar')
    }

    function handleLogout() {
        localStorage.clear()
        sessionStorage.clear()
        deleteAllCookies()
        context.logOut()
        handleClose()
    }

    return (
        <DialogContent dividers>
            <Typography gutterBottom onClick={handleChangeName}>
                <Button>
                    change name
                </Button>
            </Typography>
            <Typography gutterBottom onClick={handleChangeAvatar}>
                <Button>
                    change avatar
                </Button>
            </Typography>
            <Typography gutterBottom>
                <Button autoFocus onClick={handleLogout} color='warning'>
                    logout
                </Button>
            </Typography>
        </DialogContent>
    );
}

function ChangeNameProfile({}: DialogContentProps) {
    const [name, setName] = useState('')

    function handleCreate() {
        replaceUserName(name).then(() => window.location.reload())
    }

    return (
        <DialogContent dividers>
            <Typography>
                <textarea
                    placeholder='Enter new name...'
                    value={name}
                    onChange={e => setName(e.target.value)}
                />
            </Typography>
            <Typography>
                <Button color='success' onClick={handleCreate}>
                    Save
                </Button>
            </Typography>
        </DialogContent>
    );
}

function prompts(prompt: string): PromptInfo {
    switch (prompt) {
        case 'main':
            return {
                body({setPromptName, handleClose}: DialogContentProps) {
                    return <MainProfile setPromptName={setPromptName} handleClose={handleClose}/>
                },
                title: (
                    <ListItem>
                        <UserMeInfo>
                            <ListItemAvatar>
                                <UserAvatar/>
                            </ListItemAvatar>
                            <UserName/>
                        </UserMeInfo>
                    </ListItem>

                )
            }
        case 'change-name':
            return {
                body({setPromptName, handleClose}: DialogContentProps) {
                    return <ChangeNameProfile setPromptName={setPromptName} handleClose={handleClose}/>
                },
                title: (
                    <ListItem>
                        <UserMeInfo>
                            <ListItemAvatar>
                                <UserAvatar/>
                            </ListItemAvatar>
                            <UserName/>
                        </UserMeInfo>
                    </ListItem>
                )
            }
    }
    throw new Error(prompt)
}

export function Profile({open, handleClose}: Props) {
    const prompt = useSmartPrompt('main', prompts, open, handleClose)
    return prompt
}

function deleteAllCookies() {
    document.cookie.split(';').forEach(cookie => {
        const eqPos = cookie.indexOf('=');
        const name = eqPos > -1 ? cookie.substring(0, eqPos) : cookie;
        document.cookie = name + '=;expires=Thu, 01 Jan 1970 00:00:00 GMT';
    });
}