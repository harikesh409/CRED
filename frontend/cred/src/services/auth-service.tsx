import axios from "axios"

const baseUrl = process.env.REACT_APP_API_BASE_URL

export function loginApi(payload:any, success:(response:any) => any, error:(error:any) => any) {
    axios.post(baseUrl + "login", payload)
        .then(res => success(res))
        .catch(err => error(err))
}

export function signupApi(payload:any, success:(response:any) => any, error:(error:any) => any) {
    axios.post(baseUrl + "signup", payload)
        .then(res => success(res))
        .catch(err => error(err))
}