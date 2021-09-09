import React from 'react';
import PropTypes from 'prop-types';
import {GoogleAuthProvider, FacebookAuthProvider, UserCredential} from "firebase/auth";
import ReactTooltip from "react-tooltip";
import './login.css';
import {auth} from "../../firebase";
import userToken from "./userToken";
import {Redirect} from "react-router-dom";

export default class Login extends React.Component {
    constructor(props) {
        super(props);
        this.setToken = props.setToken;
        this.state = {
            username: '',
            password: ''
        };

        // Bind our own functions so we will not have "this is undefined" errors
        // from within those methods.. Specifically userSignInSuccess, which needs
        // the setToken function. This is an issue with React component.
        this.onUsernameChange = this.onUsernameChange.bind(this);
        this.onPwdChange = this.onPwdChange.bind(this);
        this.onFormSubmit = this.onFormSubmit.bind(this);
        this.onFacebookButtonClicked = this.onFacebookButtonClicked.bind(this);
        this.onGoogleButtonClicked = this.onGoogleButtonClicked.bind(this);
        this.userSignInSuccess = this.userSignInSuccess.bind(this);
        this.userSignInFailure = this.userSignInFailure.bind(this);
    }

    onUsernameChange(e) {
        this.setState({username: e.target.value});
    }

    onPwdChange(e) {
        this.setState({password: e.target.value});
    }

    async onFormSubmit(e) {
        // Prevent from default action to be taken, as we handle it here
        e.preventDefault();

        let credentials = {
            username: this.state.username,
            password: this.state.password
        };

        auth.signInWithEmailAndPassword(credentials.username, credentials.password)
            .then(this.userSignInSuccess)
            .catch(this.userSignInFailure);
    }

    async onFacebookButtonClicked(e) {
        const provider = new FacebookAuthProvider();

        auth.signInWithRedirect(provider)
            .then(this.userSignInSuccess)
            .catch(this.userSignInFailure);
    }

    async onGoogleButtonClicked(e) {
        const provider = new GoogleAuthProvider();
        provider.addScope('https://www.googleapis.com/auth/contacts.readonly');
        provider.setCustomParameters({
            'login_hint': 'user@example.com'
        });

        auth.signInWithRedirect(provider)
            .then(this.userSignInSuccess)
            .catch(this.userSignInFailure);
    }

    userSignInSuccess(userCredential: UserCredential) {
        // The signed-in user info.
        const user = userCredential.user;

        this.setToken(user.uid);

        // Go to home page after successful sign in
        window.location.href = '/home';
    }

    userSignInFailure(error) {
        // Handle Errors here.
        let errorCode = error.code;
        let errorMessage = error.message;
        // The email of the user's account used.
        let email = error.email;
        // The firebase.auth.AuthCredential type that was used.
        let credential = error.credential;

        console.error(error);
    }

    render() {
        // If user is already signed in, redirect to the home page.
        if (userToken.token !== undefined && userToken.token !== '') {
            return (<Redirect to="/home" />);
        }

        return (
            <div className='frame'>
                <Logo/>
                <form onSubmit={(evt) => this.onFormSubmit(evt)}>
                    <FormInput type='email' placeholder='email'
                               onChange={this.onUsernameChange.bind(this)}/>
                    <FormInput type='password' placeholder='password'
                               onChange={this.onPwdChange.bind(this)}/>
                    <button type="submit">Sign In</button>
                </form>
                <OtherSignInMethods onGoogleClick={this.onGoogleButtonClicked.bind(this)}
                                    onFacebookClick={this.onFacebookButtonClicked.bind(this)}/>
                <SignUp/>
            </div>
        )
    }
};

Login.propTypes = {
    setToken: PropTypes.func.isRequired
};

class Logo extends React.Component {
    render() {
        return (<div className="logo">
            <i><img src={"/logo192.png"} alt={"Logo"}/></i>
            <span> Sign in </span>
        </div>);
    }
}

// Generic input field
class FormInput extends React.Component {
    render() {
        return (
            <div className='row'>
                <label id='label'>{this.props.description}</label>
                <div className='edit'>
                    <input type={this.props.type} placeholder={this.props.placeholder}
                           onChange={this.props.onChange} required autoComplete='false'/>
                    <label className='labelDrawable'/>
                </div>
            </div>
        );
    }
}

class OtherSignInMethods extends React.Component {
    render() {
        return (
            <div id="alternativeLogin">
                <label>Or sign in with:</label>
                <div id="iconGroup">
                    <button id="facebookIcon" onClick={this.props.onGoogleClick} data-tip data-for="facebookTip"/>
                    <ReactTooltip id="facebookTip" place="top" effect="solid" type="info">
                        Facebook
                    </ReactTooltip>

                    <button id="googleIcon" onClick={this.props.onGoogleClick} data-tip data-for="googleTip"/>
                    <ReactTooltip id="googleTip" place="top" effect="solid" type="error">
                        Google
                    </ReactTooltip>
                </div>
            </div>
        );
    }
}

class SignUp extends React.Component {
    render() {
        return (
            <div className='signup'>
                <a href='/signup'>SIGN UP</a>
                <label/>
                <a href='/reset'>Help</a>
            </div>
        );
    }
}
