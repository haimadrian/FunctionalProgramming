import React from 'react';
import userToken from "../user/userToken";
import "./home.css";
import {Redirect} from "react-router-dom";
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';

export default function Home() {
    // If user is not signed in, redirect to the sign in page.
    if (userToken.token === undefined || userToken.token === '') {
        return (<Redirect to="/signin" />);
    }

    return (
        <div className="Home">
            <div className="Home-header">
                <img src={"/logo192.png"} alt={"Logo"} className="Home-logo" width={100}/>
                <label className='header-title'>Expense Management</label>
            </div>
            <div className='signout'>
                <a href='/signout'>Sign Out</a>
            </div>
            <Tabs>
                <TabList>
                    <Tab>
                        <p>Profile</p>
                    </Tab>
                    <Tab>
                        <p>Manage</p>
                    </Tab>
                    <Tab>
                        <p>Statistics</p>
                    </Tab>
                </TabList>

                <TabPanel>
                    <div className="panel-content">
                        <Profile/>
                    </div>
                </TabPanel>
                <TabPanel>
                    <div className="panel-content">
                        <Manage/>
                    </div>
                </TabPanel>
                <TabPanel>
                    <div className="panel-content">
                        <Statistics/>
                    </div>
                </TabPanel>
            </Tabs>

        </div>
    );
}

class Profile extends React.Component {
    render() {
        return (<div className="logo">
            <i><img src={"/logo192.png"} alt={"Logo"}/></i>
            <span> Sign in </span>
        </div>);
    }
}

class Manage extends React.Component {
    render() {
        return (<div className="logo">
            <i><img src={"/logo192.png"} alt={"Logo"}/></i>
            <span> Sign in </span>
        </div>);
    }
}

class Statistics extends React.Component {
    render() {
        return (<div className="logo">
            <i><img src={"/logo192.png"} alt={"Logo"}/></i>
            <span> Sign in </span>
        </div>);
    }
}