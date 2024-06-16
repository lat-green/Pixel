import {styled} from "@mui/material/styles";
import Dialog from "@mui/material/Dialog";
import * as React from 'react';
import {PropsWithChildren, ReactNode, useCallback, useState} from 'react';
import DialogTitle from '@mui/material/DialogTitle';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';

interface Props {
    isOpen: boolean,
    handleClose: () => void,
    handleBack?: () => void,
    title: ReactNode
}

const BootstrapDialog = styled(Dialog)(({theme}) => ({
    '& .MuiDialogContent-root': {
        padding: theme.spacing(2),
    },
    '& .MuiDialogActions-root': {
        padding: theme.spacing(1),
    },
}));

export function usePrompt(): [boolean, () => void, () => void] {
    const [isOpen, setIsOpen] = useState(false)

    const handleOpen = useCallback(() => {
        setIsOpen(true);
    }, [setIsOpen]);

    const handleClose = useCallback(() => {
        setIsOpen(false);
    }, [setIsOpen]);

    return [isOpen, handleOpen, handleClose]
}

export default function Prompt({isOpen, handleClose, handleBack, title, children}: PropsWithChildren<Props>) {
    return (
        <BootstrapDialog
            onClose={handleClose}
            open={isOpen}
        >
            <DialogTitle
                style={{
                    minWidth: '400px'
                }}
                id="customized-dialog-title">
                {title}
            </DialogTitle>
            {
                handleBack
                    ?
                    <IconButton
                        aria-label="back"
                        onClick={handleBack}
                        sx={{
                            position: 'absolute',
                            right: 38,
                            top: 8,
                            color: (theme) => theme.palette.grey[500],
                        }}
                    >
                        <ArrowBackIcon/>
                    </IconButton>
                    : null
            }
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
            {children}
        </BootstrapDialog>
    )
}