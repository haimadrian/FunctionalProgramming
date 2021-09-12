import React from 'react';
import {BrowserRouter, Route, Switch, Redirect} from 'react-router-dom';
import './App.css';
import './font-awesome.min.css';
import Login from './view/user/login';
import Logout from './view/user/logout';
import userToken from './view/user/userToken';
import Home from './view/home/home';
import {auth} from "./firebase";

function App() {
    userToken.loadToken();

    // Listen to id token updates so we can persist it.
    auth.onIdTokenChanged(user => {
        if (user !== null) {
            user.getIdToken(true).then(idToken => {
                userToken.saveToken(idToken);
            });
        }
    });

    return (
        <div className="App">
            <BrowserRouter>
                <Switch>
                    <Route exact path="/" render={() => {
                        return (<Redirect to="/home"/>);
                    }
                    }
                    />
                    <Route path="/home">
                        <Home/>
                    </Route>
                    <Route path="/signin">
                        <Login/>
                    </Route>
                    <Route path="/signout">
                        <Logout setToken={userToken.saveToken}/>
                    </Route>
                    <Route path="/signup">
                        <Home/>
                    </Route>
                </Switch>
            </BrowserRouter>
        </div>
    );
}

export default App;
