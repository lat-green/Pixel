import React from "react";
import {UserName} from "./user/User";
import {Link} from "react-router-dom";

export function Profile() {

    return <>
        <div className={"user-profile"}>
            <UserName/>
            <Link to={"/"}>
                Home
            </Link>
        </div>
    </>
}