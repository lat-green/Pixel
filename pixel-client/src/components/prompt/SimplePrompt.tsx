import Prompt from "./Prompt";
import Typography from "@mui/material/Typography";
import {Button} from "@mui/material";
import * as React from "react";
import {useState} from "react";
import {TextInput} from "react-native/types";

interface Props {
    isOpen: boolean,
    handleClose: () => void,
    handleSuccess: (text: string) => void,
    title: string
}

export default function SimplePrompt({title, isOpen, handleClose, handleSuccess}: Props) {
    const [text, setText] = useState('')

    return (
        <Prompt isOpen={isOpen} handleClose={handleClose} title={title}>
            <Typography gutterBottom>
                <TextInput onChangeText={setText} value={text}/>
            </Typography>
            <Typography gutterBottom onClick={() => handleSuccess(text)}>
                <Button color='success'>Ок</Button>
            </Typography>
        </Prompt>
    )
}
