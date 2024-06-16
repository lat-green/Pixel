import React, {useContext, useEffect} from 'react';
import {Route, Routes, useParams} from "react-router-dom";
import {Paper, Skeleton, styled} from "@mui/material";
import {UserMeInfo} from "./components/user/User";
import {AuthContext} from "react-oauth2-code-pkce";

import './App.css'
import Home from "./components/mui/Home";
import {joinRoom} from "./api/data/Room";

const Item = styled(Paper)(({theme}) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));

function AuthCode(): JSX.Element | null {
    window.location.href = '/'
    return null
}

function AuthSignIn(): JSX.Element | null {
    const context = useContext(AuthContext)
    useEffect(() => {
        context.logIn()
    }, []);
    return null
}

function JoinPage(): JSX.Element | null {
    const {chatId} = useParams()
    if (chatId)
        joinRoom(parseInt(chatId))
            .then(() => {
                window.location.href = '/'
            })
    return null
}

export default function App() {
    const context = useContext(AuthContext)

    if (context.loginInProgress)
        return (<Skeleton/>)

    const {token} = context
    if (token && token !== '') {
        localStorage.setItem('access_token', token)
    }

    return (
        <Routes>
            <Route path="/auth/code" element={<AuthCode/>}/>
            <Route path="/auth/signin" element={<AuthSignIn/>}/>
            <Route path="/join/:chatId" element={<JoinPage/>}/>
            <Route path="/" element={<UserMeInfo><Home/></UserMeInfo>}/>
        </Routes>
    );
}
