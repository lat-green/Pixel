import {AnonymousOnly, AuthorizationOnly, UserName} from "./user/User"
import React, {useCallback} from "react";


export function AuthButtons() {
    const onSignIn = useCallback(() => {
        console.log("Log In")
        window.location.href = '/signin'
    }, [])
    const onSignUp = useCallback(() => {
        console.log("Log Up")
    }, [])
    const onLogOut = useCallback(() => {
        console.log("Log Out")
    }, [])

    return <>
        <AnonymousOnly>
            <button onClick={onSignIn}>Sign In</button>
            <button onClick={onSignUp}>Sign Up</button>
        </AnonymousOnly>
        <AuthorizationOnly>
            <UserName/>
            <button onClick={onLogOut}>Log Out</button>
        </AuthorizationOnly>
    </>
}
