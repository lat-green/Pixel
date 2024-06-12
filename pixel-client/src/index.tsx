import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {BrowserRouter} from "react-router-dom";
import {AuthProvider} from "react-oauth2-code-pkce";
import {authConfig} from "./api/ServerAuthUtil";
import {StompSessionProvider} from "react-stomp-hooks";

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);
root.render(
    <BrowserRouter>
        <StompSessionProvider
            url={"http://localhost:3032/ws"}
        >
            <AuthProvider authConfig={authConfig}>
                <App/>
            </AuthProvider>
        </StompSessionProvider>
    </BrowserRouter>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
