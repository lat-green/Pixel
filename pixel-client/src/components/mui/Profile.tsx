import * as React from 'react';
import {useContext} from 'react';
import {styled} from '@mui/material/styles';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import Typography from '@mui/material/Typography';
import {UserAvatar, UserMeInfo, UserName} from "../user/User";
import {Button, ListItem, ListItemAvatar} from '@mui/material';
import {AuthContext} from "react-oauth2-code-pkce";

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


export function Profile({open, handleClose}: Props) {
    const context = useContext(AuthContext)

    function handleChangeName() {
        console.log("handleChangeName")
        handleClose()
    }

    function handleChangeAvatar() {
        console.log("handleChangeAvatar")
        handleClose()
    }

    function handleLogout() {
        localStorage.clear()
        sessionStorage.clear()
        context.logOut()
        handleClose()
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
                <ListItem>
                    <UserMeInfo>
                        <ListItemAvatar>
                            <UserAvatar/>
                        </ListItemAvatar>
                        <UserName/>
                    </UserMeInfo>
                </ListItem>
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
        </BootstrapDialog>
    )
}