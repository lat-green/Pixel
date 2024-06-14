import * as React from 'react';
import {styled} from '@mui/material/styles';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import Typography from '@mui/material/Typography';
import {Button} from '@mui/material';
import {createGroupRoom} from "../../api/data/Room";

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

export function CreateChat({open, handleClose}: Props) {

    function handleCreateGroup() {
        const title = prompt('Enter title of chat');
        if (title)
            createGroupRoom({
                title: title
            })
        handleClose()
    }

    function handleCreateChannel() {

    }

    return (
        <BootstrapDialog
            onClose={handleClose}
            open={open}
        >
            <DialogTitle
                style={{
                    minWidth: '400px'
                }}
                id="customized-dialog-title">
                Create chat
            </DialogTitle>
            <IconButton
                aria-label="close"
                onClick={handleClose}
                sx={{
                    position: 'absolute',
                    right: 8,
                    top: 8,
                    color: (theme) => theme.palette.grey[500],
                }}
            >
                <CloseIcon/>
            </IconButton>
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
        </BootstrapDialog>
    )
}

function deleteAllCookies() {
    document.cookie.split(';').forEach(cookie => {
        const eqPos = cookie.indexOf('=');
        const name = eqPos > -1 ? cookie.substring(0, eqPos) : cookie;
        document.cookie = name + '=;expires=Thu, 01 Jan 1970 00:00:00 GMT';
    });
}