import React from 'react';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import './App.css';
import Login from './user/login';
import userToken from './user/userToken';
import Dashboard from './dashboard/dashboard';

function App() {
    userToken.loadToken();

    if (userToken.token === undefined || userToken.token === '') {
        return (<Login setToken={ userToken.saveToken }/>);
    }

    return (
        <div className="App">
            <h1>Expense Management Application</h1>
            <BrowserRouter>
                <Switch>
                    <Route path="/home">
                        <Dashboard/>
                    </Route>
                    <Route path="/signin">
                        <Login/>
                    </Route>
                </Switch>
            </BrowserRouter>
        </div>
    );
}

export default App;
