import React, {PropsWithChildren, SetStateAction} from 'react'
import {FileUploader} from "react-drag-drop-files";

const fileTypes = ["JPG", "PNG", "GIF"];

interface Props {
    handleChangeFile: (file: SetStateAction<File>) => void
}

export default function DragDrop({children, handleChangeFile}: PropsWithChildren<Props>) {
    return (
        <FileUploader onDrop={handleChangeFile} types={fileTypes} disabled={true} required={true}>
            {children}
        </FileUploader>
    );
}
