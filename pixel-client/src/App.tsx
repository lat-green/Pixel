import React from 'react';
import "./App.css"
import {BrowserRouter as Router, Link, Route, Routes, useSearchParams} from "react-router-dom";

function Home() {
    return <>
        <header>
            {/*<Link to="/">Home</Link>*/}
            <Link to="/auth/signin">SignIn</Link>
            {/*<Link to="/auth/signup">SignUp</Link>*/}
        </header>
    </>
}

function AuthSignIn(): JSX.Element | null {
    console.log("a")
    window.location.href = "http://localhost:7777/oauth2/authorize?response_type=code&client_id=test-client&redirect_uri=http://localhost:3000/auth/code&scope=read write"
    return null
}

interface Tokens {
    access_token: string,
    refresh_token: string,
    token_type: string,
}

async function getTokens(code: string): Promise<Tokens> {
    'use server';

    console.log("server")

    return fetch(`http://localhost:7777/oauth2/token?grant_type=authorization_code&code=${code}&redirect_uri=http://localhost:3000/auth/code`, {
        method: 'POST',
        headers: {
            'Authorization': "Basic dGVzdC1jbGllbnQ6dGVzdC1jbGllbnQ="
        }
    }).then(resp => resp.json());
}

function AuthCode(): JSX.Element | null {
    const [params, _] = useSearchParams()
    const code = params.get('code')!!

    getTokens(code).then(json => {
        const access_token = json.access_token
        if (access_token)
            sessionStorage.setItem('access_token', json.access_token)
        window.location.href = '/';
    })
    return null
}

export default function App() {
    return (
        <Router>
            <Routes>
                <Route path="/auth/code" element={<AuthCode/>}/>
                <Route path="/auth/signin" element={<AuthSignIn/>}/>
                <Route path="/" element={<Home/>}/>
            </Routes>
        </Router>
    );
}
