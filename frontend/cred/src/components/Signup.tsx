import React, { Component } from 'react';
import { Navbar } from 'react-bootstrap';
import { SignupState } from '../models/authState';
import { FormState } from '../models/formState';
import { isEmailValid, isMobileNumberValid } from '../util/Utils';
import { ProgressBar } from 'react-bootstrap';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { RootState } from '../reducers';
import { signup } from '../actions/auth-action'
import { CustomNavBrand } from './CustomNavBrand'
import "../styles/login.css"

interface SignupProp {
    signupState: SignupState,
    history: any,
    signup: (payload: any) => void
}

const EMAIL = "email"
const PASSWORD = "password"
const FIRST_NAME = "firstName"
const LAST_NAME = "lastName"
const MOBILE_NUMBER = "mobileNumber"

class Signup extends Component<SignupProp, FormState> {
    constructor(props: SignupProp) {
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
            firstName: this.state.fields[FIRST_NAME],
            lastName: this.state.fields[LAST_NAME],
            mobileNumber: this.state.fields[MOBILE_NUMBER],
            emailId: this.state.fields[EMAIL],
            password: this.state.fields[PASSWORD]
        }
        this.props.signup(payload)
    }
    isFormValid = () => {
        let fields = this.state.fields;
        let errors = this.state.errors;
        let formValid = true;
        if (!fields[FIRST_NAME]) {
            errors[FIRST_NAME] = "First name cannot be empty";
            formValid = false;
        } else if (!fields[LAST_NAME]) {
            errors[LAST_NAME] = "Last name cannot be empty";
            formValid = false;
        } else if (!isEmailValid(fields[EMAIL])) {
            errors[EMAIL] = "Invalid email";
            formValid = false;
        } else if (!fields[PASSWORD]) {
            errors[PASSWORD] = "Password cannot be empty";
            formValid = false;
        } else if (!isMobileNumberValid(fields[MOBILE_NUMBER])) {
            errors[MOBILE_NUMBER] = "Invalid mobile number";
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
        if(this.props.signupState.success) {
            this.props.history.replace('/')
        }
        return (
            <div>
                <Navbar expand="lg" sticky="top" variant="light">
                    <CustomNavBrand/>
                </Navbar>
                <form className="form">
                    <h4 className="signup-form-heading">Sign up</h4>
                    <div className="form-group">
                        <label>First name</label>
                        <input 
                            type="text" 
                            className="form-control" 
                            placeholder="First name"
                            onChange={this.handleChange.bind(this, FIRST_NAME)} />
                        <span className="error-message">{this.state.errors[FIRST_NAME]}</span>
                    </div>
                    <div className="form-group">
                        <label>Last name</label>
                        <input 
                            type="text" 
                            className="form-control" 
                            placeholder="Last name"
                            onChange={this.handleChange.bind(this, LAST_NAME)} />
                        <span className="error-message">{this.state.errors[LAST_NAME]}</span>
                    </div>
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
                    <div className="form-group">
                        <label>Mobile number</label>
                        <input 
                            type="text" 
                            className="form-control" 
                            placeholder="Mobile number"
                            onChange={this.handleChange.bind(this, MOBILE_NUMBER)} />
                        <span className="error-message">{this.state.errors[MOBILE_NUMBER]}</span>
                    </div>
                    <button 
                        type="submit" 
                        className="btn btn-primary btn-block"
                        disabled={this.props.signupState.inProgress}
                        onClick={this.handleSubmit.bind(this)}>
                        Sign Up
                    </button>
                    {this.props.signupState.inProgress ? 
                            <ProgressBar className="progressbar" animated now={100} /> : ""}
                    {this.props.signupState.error ? <span className="row d-flex justify-content-center error-message">{this.props.signupState.error}</span> : ""}
                    <p className="forgot-password text-right">
                        Already registered? <a href="/login">Log in</a>
                    </p>
                </form>
            </div>
        )
    }
}
const mapStateToProps = (state: RootState) => {
    return { signupState: state.authReducer.signupState };
};
const mapDispatchToProps = (dispatch: any) => {
    return {
        ...bindActionCreators({ signup }, dispatch)
    };
};
export default connect(mapStateToProps, mapDispatchToProps)(Signup);