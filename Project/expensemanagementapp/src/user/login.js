import React, {useState} from 'react';
import PropTypes from 'prop-types';
import './login.css';

async function loginUser(credentials) {
    return fetch('http://localhost:9001/signin', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(credentials)
    })
        .then(data => data.json())
        .catch(error => {
            console.error(error);
            return { token: '' };
        });
}

export default function Login({setToken}) {
    const [username, setUserName] = useState();
    const [password, setPassword] = useState();

    const handleSubmit = async e => {
        console.error('aaa');
        // Prevent from default action to be taken, as we handle it here
        e.preventDefault();

        const token = await loginUser({
            username,
            password
        });

        setToken(token);
    }

    const handleFacebook = async e => {

    }

    const handleGoogle = async e => {

    }

    return (
        <div className='Modal'>
            <Logo />
            <form onSubmit= { handleSubmit }>
                <Input type='text' name='username' placeholder='email' onChange={e => setUserName(e.target.value)} />
                <Input type='password' name='password' placeholder='password' onChange={e => setPassword(e.target.value)} />
                <button type="submit">Sign In</button>
            </form>
            <div className='social-signin'>
                <ImageButton clsName="fb" onClick={handleFacebook} text={"Facebook"}/>
                <ImageButton clsName="go" onClick={handleGoogle} text={"Google"}/>
            </div>
            <div className='signup'>
                <a href='/signup'>SIGN UP</a>
                <label/>
                <a href='/reset'>Forgot password</a>
            </div>
        </div>
    );
}

Login.propTypes = {
    setToken: PropTypes.func.isRequired
};

// Generic input field
class Input extends React.Component {
    render() {
        return (<div className='Input'>
            <input type={ this.props.type } name={ this.props.name } placeholder={ this.props.placeholder } required autoComplete='false'/>
            <label />
        </div>);
    }
}

class ImageButton extends React.Component {
    render() {
        return (<div className='ImageButton' id={ this.props.clsName }>
            <button name={ this.props.clsName } onClick={ this.props.onClick }>{ this.props.text }</button>
            <label />
        </div>);
    }
}

// Fake logo
class Logo extends React.Component {
    render() {
        return (<div className="logo">
            <i><img src={"/logo192.png"} alt={"Logo"}/></i>
            <span> Sign in </span>
        </div>);
    }
}
