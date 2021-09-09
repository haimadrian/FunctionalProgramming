import React from 'react';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import './App.css';
import './font-awesome.min.css';
import Login from './view/user/login';
import Logout from './view/user/logout';
import userToken from './view/user/userToken';
import Home from './view/home/home';

function App() {
    userToken.loadToken();

    return (
        <div className="App">
            <BrowserRouter>
                <Switch>
                    <Route path="/home">
                        <Home/>
                    </Route>
                    <Route path="/signin">
                        <Login setToken={userToken.saveToken}/>
                    </Route>
                    <Route path="/signout">
                        <Logout setToken={userToken.saveToken}/>
                    </Route>
                </Switch>
            </BrowserRouter>
        </div>
    );
}

export default App;
