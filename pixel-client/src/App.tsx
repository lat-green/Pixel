import React from 'react';
import {Link, Route, Routes, useSearchParams} from "react-router-dom";
import {getTokens} from "./api/ServerAuthUtil";
import {AnonymousOnly, AuthorizationOnly, UserMeInfo, UserName} from "./components/user/User";
import {Paper, styled} from "@mui/material";
import {Contacts} from "./components/Contacts";
import Chat from "./components/Chat";
import {AUTH_URI, SERVER_IP} from "./api/DataUtil";

const Item = styled(Paper)(({theme}) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
}));

function Home() {
    return <>
        <header>
            <ul>
                <li><Link to="/">Home</Link></li>
                <AnonymousOnly>
                    <li><Link to="/auth/signin">SignIn</Link></li>
                    <li><Link to="/auth/signup">SignUp</Link></li>
                </AnonymousOnly>
                <AuthorizationOnly>
                    Hello, <UserName/>.
                    <li><Link to="/auth/logout">LogOut</Link></li>
                </AuthorizationOnly>
            </ul>
            <AuthorizationOnly>
                <Contacts/>
            </AuthorizationOnly>
        </header>
    </>
}

function AuthSignIn(): JSX.Element | null {
    window.location.href = `${AUTH_URI}/oauth2/authorize?response_type=code&client_id=test-client&redirect_uri=http://${SERVER_IP}:3000/auth/code&scope=read write`
    return null
}

function AuthSignUp(): JSX.Element | null {
    window.location.href = `${AUTH_URI}/oauth2/registration?response_type=code&client_id=test-client&redirect_uri=http://${SERVER_IP}:3000/auth/code&scope=read write`
    return null
}

function AuthLogOut(): JSX.Element | null {
    sessionStorage.removeItem("access_token")
    window.location.href = "/"
    return null
}

function AuthCode(): JSX.Element | null {
    const [params, _] = useSearchParams()
    const code = params.get('code')!!

    getTokens(code).then(tokens => {
        const access_token = tokens.access_token
        if (access_token)
            sessionStorage.setItem('access_token', tokens.access_token)
        window.location.href = '/';
    })
    return null
}

export default function App() {
    return (
        <Routes>
            <Route path="/auth/code" element={<AuthCode/>}/>
            <Route path="/auth/signin" element={<AuthSignIn/>}/>
            <Route path="/auth/signup" element={<AuthSignUp/>}/>
            <Route path="/auth/logout" element={<AuthLogOut/>}/>
            <Route path="/" element={<UserMeInfo><Home/></UserMeInfo>}/>
            <Route path="/chat/:id" element={<UserMeInfo><Chat/></UserMeInfo>}/>
        </Routes>
    );
}
