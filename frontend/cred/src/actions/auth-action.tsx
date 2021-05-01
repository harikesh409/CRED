import { LOGIN_SUCCESS, LOGIN_FAILED, LOGIN_PROGRESS } from "../constants/action-types";
import { LOGOUT_SUCCESS, LOGOUT_PROGRESS } from "../constants/action-types";
import { SIGNUP_SUCCESS, SIGNUP_FAILED, SIGNUP_PROGRESS } from "../constants/action-types";
import { loginApi, signupApi } from '../services/auth-service'

export function login(payload:any) {
    return function (dispatch:any) {
        dispatch({type:LOGIN_PROGRESS, payload:""})
        loginApi(payload, (response: any) => {
            dispatch({type:LOGIN_SUCCESS, payload:response})
        }, (error:any) => {
            dispatch({type:LOGIN_FAILED, payload:error.message})
        })
    }
};

export function signup(payload:any) {
    return function (dispatch:any) {
        dispatch({type:SIGNUP_PROGRESS, payload:""})
        signupApi(payload, (response: any) => {
            dispatch({type:SIGNUP_SUCCESS, payload:response})
        }, (error:any) => {
            dispatch({type:SIGNUP_FAILED, payload:error.message})
        })
    }
};

export function logout() {
    return function (dispatch:any) {
        dispatch({type:LOGOUT_PROGRESS, payload:""})
        dispatch({type:LOGOUT_SUCCESS, payload:""})
    }
};