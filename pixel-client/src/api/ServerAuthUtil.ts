import {AUTH_TOKEN_URI, AUTH_URI, CLIENT_URI} from "./FetchUtil";
import {TAuthConfig, TRefreshTokenExpiredEvent} from "react-oauth2-code-pkce";

export const authConfig: TAuthConfig = {
    clientId: 'test-client',

    authorizationEndpoint: `${AUTH_URI}/oauth2/authorize`,
    tokenEndpoint: `${AUTH_TOKEN_URI}/token`,

    redirectUri: `${CLIENT_URI}/auth/code`,
    scope: 'profile.write chat.write openid',
    onRefreshTokenExpire: (event: TRefreshTokenExpiredEvent) => window.confirm('Session expired. Refresh page to continue using the site?') && event.logIn(),

    logoutRedirect: '/',
    storage: 'local',


    logoutEndpoint: '/',
}