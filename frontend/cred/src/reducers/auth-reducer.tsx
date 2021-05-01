import { LOGIN_SUCCESS, LOGIN_FAILED, LOGIN_PROGRESS } from "../constants/action-types";
import { LOGOUT_SUCCESS, LOGOUT_FAILED, LOGOUT_PROGRESS } from "../constants/action-types";
import { SIGNUP_SUCCESS, SIGNUP_FAILED, SIGNUP_PROGRESS } from "../constants/action-types";
import { LoginState, SignupState, LogoutState } from '../models/authState'
import { USER_TOKEN } from '../constants/store-constants'

const defaultLoginState: LoginState = {
  inProgress: false,
  success: false,
  error: ""
}

const defaultSignupState: SignupState = {
  inProgress: false,
  success: false,
  error: ""
}

const defaultLogoutState: LogoutState = {
  inProgress: false,
  success: false,
  error: ""
}

const defaultState = {
    loginState: defaultLoginState,
    signupState: defaultSignupState,
    logoutState: defaultLogoutState
};
  
  function authReducer(state = defaultState, action:any) {
    switch(action.type) {
      case LOGIN_SUCCESS:{
        saveUserToken(action.payload.data)
        const newState = {
          ...state.loginState
        }
        newState.inProgress = false
        newState.success = true
        newState.error = ""
        return {
          ...state,
          loginState: newState,
          logoutState: defaultLogoutState
        }
      }
      case LOGIN_FAILED: {
        const newState = {
          ...state.loginState
        }
        newState.inProgress = false
        newState.success = false
        newState.error = action.payload
        return {
          ...state,
          loginState: newState
        }
      }

      case LOGIN_PROGRESS: {
        const newState = {
          ...state.loginState
        }
        newState.inProgress = true
        newState.success = false
        newState.error = ""
        return {
          ...state,
          loginState: newState
        }
      }
      case SIGNUP_SUCCESS:{
        saveUserToken(action.payload.data)
        const newState = {
          ...state.signupState
        }
        newState.inProgress = false
        newState.success = true
        newState.error = ""
        return {
          ...state,
          signupState: newState,
          logoutState: defaultLogoutState
        }
      }
      case SIGNUP_FAILED: {
        const newState = {
          ...state.signupState
        }
        newState.inProgress = false
        newState.success = false
        newState.error = action.payload
        return {
          ...state,
          signupState: newState
        }
      }

      case SIGNUP_PROGRESS: {
        const newState = {
          ...state.signupState
        }
        newState.inProgress = true
        newState.success = false
        newState.error = ""
        return {
          ...state,
          signupState: newState
        }
      }
      case LOGOUT_SUCCESS:{
        removeUserToken()
        const newState = {
          ...state.logoutState
        }
        newState.inProgress = false
        newState.success = true
        newState.error = ""
        return {
          ...state,
          logoutState: newState
        }
      }
      case LOGOUT_FAILED: {
        const newState = {
          ...state.logoutState
        }
        newState.inProgress = false
        newState.success = false
        newState.error = action.payload
        return {
          ...state,
          logoutState: newState
        }
      }

      case LOGOUT_PROGRESS: {
        const newState = {
          ...state.logoutState
        }
        newState.inProgress = true
        newState.success = false
        newState.error = ""
        return {
          ...state,
          logoutState: newState
        }
      }
      default:
      return state;
    }
  };

  const saveUserToken = (response: any) => {
    localStorage.setItem(USER_TOKEN, response.token)
  }

  const removeUserToken = () => {
    localStorage.removeItem(USER_TOKEN)
  }
  
  export default authReducer;