import React from "react";

export default class Profile extends React.Component {
    render() {
        return (<div className="logo">
            <i><img src={"/logo192.png"} alt={"Logo"}/></i>
            <span> Sign in </span>
        </div>);
    }
}