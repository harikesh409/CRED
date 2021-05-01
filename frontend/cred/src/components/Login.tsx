import React, { Component } from 'react';
import { Navbar, Button, Nav } from 'react-bootstrap';
import { connect } from 'react-redux';
import { login } from '../actions/auth-action'
import { bindActionCreators } from 'redux';
import { RootState } from '../reducers';
import { LoginState } from '../models/authState';
import { FormState } from '../models/formState'
import { isEmailValid } from '../util/Utils'
import { ProgressBar } from 'react-bootstrap';
import { CustomNavBrand } from './CustomNavBrand'
import "../styles/login.css"

interface LoginProp {
    loginState: LoginState,
    history: any,
    login: (payload: any) => void
}

const EMAIL = "emailId"
const PASSWORD = "password"

class Login extends Component<LoginProp, FormState> {
    constructor(props: LoginProp) {
        super(props)
        this.state = {
            fields: {},
            errors: {},
        }
    }
    handleChange(field: string, e: React.ChangeEvent<HTMLInputElement>) {
        let fields = this.state.fields;
        fields[field] = e.target.value;
        this.setState({ fields });
        this.setState({ errors: {} });
    }
    handleSubmit = (e: React.ChangeEvent<any>) => {
        e.preventDefault()
        if (!this.isFormValid()) {
            return
        }
        const payload = {
            emailId: this.state.fields[EMAIL],
            password: this.state.fields[PASSWORD]
        }
        this.props.login(payload)
    }
    isFormValid = () => {
        let fields = this.state.fields;
        let errors = this.state.errors;
        let formValid = true;
        if (!isEmailValid(fields[EMAIL])) {
            errors[EMAIL] = "Invalid email";
            formValid = false;
        } else if (!fields[PASSWORD]) {
            errors[PASSWORD] = "Password cannot be empty";
            formValid = false;
        }
        if (!formValid) {
            this.setState({ errors });
        } else {
            this.setState({ errors: {} });
        }
        return formValid;
    }
    render() {
        if(this.props.loginState.success) {
            this.props.history.replace('/')
        }
        return (
            <div>
                <Navbar expand="lg" sticky="top" variant="light">
                    <CustomNavBrand/>
                    <Nav className="ml-auto">
                        <Button variant="outline-primary" href="/signup">Signup</Button>
                    </Nav>
                </Navbar>
                <form className="form">
                    <h4 className="login-form-heading">Login</h4>
                    <div className="form-group">
                        <label>Email address</label>
                        <input
                            type="email"
                            className="form-control"
                            placeholder="Enter email"
                            onChange={this.handleChange.bind(this, EMAIL)} />
                        <span className="error-message">{this.state.errors[EMAIL]}</span>
                    </div>
                    <div className="form-group">
                        <label>Password</label>
                        <input
                            type="password"
                            className="form-control"
                            placeholder="Enter password"
                            onChange={this.handleChange.bind(this, PASSWORD)} />
                        <span className="error-message">{this.state.errors[PASSWORD]}</span>
                    </div>
                    <button
                        type="submit"
                        className="btn btn-primary btn-block"
                        disabled={this.props.loginState.inProgress}
                        onClick={this.handleSubmit.bind(this)}>
                        Submit
                    </button>
                    {this.props.loginState.inProgress ? <ProgressBar className="progressbar" animated now={100} /> : ""}
                    {this.props.loginState.error ? <span className="row d-flex justify-content-center error-message">{this.props.loginState.error}</span> : ""}
                </form>
            </div>
        )
    }
}
const mapStateToProps = (state: RootState) => {
    return { loginState: state.authReducer.loginState };
};
const mapDispatchToProps = (dispatch: any) => {
    return {
        ...bindActionCreators({ login }, dispatch)
    };
};
export default connect(mapStateToProps, mapDispatchToProps)(Login);