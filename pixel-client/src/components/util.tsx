import {PropsWithChildren} from "react";
import "./util.css"

export function Right({children}: PropsWithChildren) {
    return (
        <div className="right">
            {children}
        </div>
    )
}

export function Center({children}: PropsWithChildren) {
    return (
        <div className="center">
            {children}
        </div>
    )
}

