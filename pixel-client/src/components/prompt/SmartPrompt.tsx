import React, {ReactNode, useState} from "react";
import Prompt from "./Prompt";
import {useMap} from "../HookUtil";
import usePrevious from "../hooks/usePrevious";

export interface PromptInfo {
    title: ReactNode

    body(props: DialogContentProps): React.ReactNode;
}

export interface DialogContentProps {
    setPromptName: React.Dispatch<React.SetStateAction<string>>,
    handleClose: () => void
}

export function useSmartPrompt(initState: string | (() => string) = 'main', prompts: (prompt: string) => PromptInfo, isOpen: boolean, handleClose: () => void) {
    const [promptName, setPromptName] = useState(initState)

    const promptNamePrevious = usePrevious(promptName)

    const prompt = useMap(promptName, prompts)

    if (promptNamePrevious)
        return <Prompt isOpen={isOpen} handleClose={handleClose} handleBack={() => setPromptName(promptNamePrevious)}
                       title={prompt.title}>
            {prompt.body({
                setPromptName: setPromptName,
                handleClose: handleClose
            })}
        </Prompt>

    return <Prompt isOpen={isOpen} handleClose={handleClose} title={prompt.title}>
        {prompt.body({
            setPromptName: setPromptName,
            handleClose: handleClose
        })}
    </Prompt>
}

