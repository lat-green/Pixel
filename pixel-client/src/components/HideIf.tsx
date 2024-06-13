import {PropsWithChildren} from "react";


interface Props {
    hide: boolean
}

export default function HideIf({hide, children}: PropsWithChildren<Props>): JSX.Element {
    return (
        <>
            {
                hide
                    ? null
                    : children
            }
        </>
    )
}
