import * as React from 'react';
import {useState} from 'react';
import DialogTitle from '@mui/material/DialogTitle';
import Typography from '@mui/material/Typography';
import {Button, DialogContent} from '@mui/material';
import {DialogContentProps, PromptInfo, useSmartPrompt} from "../prompt/SmartPrompt";
import {createGroupRoom} from "../../api/data/Room";

interface Props {
    isOpen: boolean,
    handleClose: () => void
}

function CreateGroupDialogContent({setPromptName}: DialogContentProps) {
    const [title, setTitle] = useState('')

    function handleCreate() {
        createGroupRoom({
            title: title
        }).then(() => window.location.reload())
    }

    return (
        <DialogContent dividers>
            <Typography>
                <textarea
                    placeholder='Enter title...'
                    value={title}
                    onChange={e => setTitle(e.target.value)}
                />
            </Typography>
            <Typography>
                <Button color='success' onClick={handleCreate}>
                    Create
                </Button>
            </Typography>
        </DialogContent>
    );
}

function MainDialogContent({setPromptName}: DialogContentProps) {

    function handleCreateGroup() {
        setPromptName('create-group')
    }

    function handleCreateChannel() {
        setPromptName('create-channel')
    }

    return (
        <DialogContent dividers>
            <Typography>
                <Button onClick={handleCreateGroup}>
                    Create group
                </Button>
            </Typography>
            <Typography>
                <Button onClick={handleCreateChannel}>
                    Create channal
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
                    return <MainDialogContent setPromptName={setPromptName} handleClose={handleClose}/>
                },
                title: (
                    <DialogTitle
                        style={{
                            minWidth: '400px'
                        }}
                        id="customized-dialog-title">
                        Create chat
                    </DialogTitle>
                )
            }
        case 'create-group':
            return {
                body({setPromptName, handleClose}: DialogContentProps) {
                    return <CreateGroupDialogContent setPromptName={setPromptName} handleClose={handleClose}/>
                },
                title: (
                    <DialogTitle
                        style={{
                            minWidth: '400px'
                        }}
                        id="customized-dialog-title">
                        Create group
                    </DialogTitle>
                )
            }

    }
    throw new Error(prompt)
}

export function CreateChat({isOpen, handleClose}: Props) {
    const prompt = useSmartPrompt('main', prompts, isOpen, handleClose)
    return prompt
}

function deleteAllCookies() {
    document.cookie.split(';').forEach(cookie => {
        const eqPos = cookie.indexOf('=');
        const name = eqPos > -1 ? cookie.substring(0, eqPos) : cookie;
        document.cookie = name + '=;expires=Thu, 01 Jan 1970 00:00:00 GMT';
    });
}