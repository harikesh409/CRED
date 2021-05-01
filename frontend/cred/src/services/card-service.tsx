import axios from "axios"
import { USER_TOKEN } from '../constants/store-constants'

const baseUrl = process.env.REACT_APP_API_BASE_URL

export function getAllCardsApi(successCallback: (response: any) => any, 
  errorCallback: (errorCallback: any) => any) {
  axios.get(baseUrl + "cards", getConfig())
    .then(res => successCallback(res))
    .catch(err => handleError(err, errorCallback))
}

export function addCardApi(payload: any, successCallback: (response: any) => any, 
  errorCallback: (errorCallback: any) => any) {
  axios.post(baseUrl + "cards", payload, getConfig())
    .then(res => successCallback(res))
    .catch(err => handleError(err, errorCallback))
}

export function getStatementApi(id: number, year: number, month: number,
  successCallback: (response: any) => any, errorCallback: (errorCallback: any) => any) {
  Promise.all([getCardStatementApi(id, year, month), 
    getSmartStatementByCategory(id, year, month), 
    getSmartStatementByVendor(id, year, month)])
    .then(res => successCallback(res))
    .catch(err => handleError(err, errorCallback))
}

function getCardStatementApi(id: number, year: number, month: number) {
  return axios.get(baseUrl + "cards/" + id + "/statements/" + year + "/" + month, getConfig())
}

function getSmartStatementByCategory(id: number, year: number, month: number) {
  return axios.get(baseUrl + "cards/" + id + "/category/smart-statement/" + year + "/" + month, getConfig())
}

function getSmartStatementByVendor(id: number, year: number, month: number) {
  return axios.get(baseUrl + "cards/" + id + "/vendor/smart-statement/" + year + "/" + month, getConfig())
}

export function payBillApi(id: string, payload: any, 
  successCallback: (response: any) => any, errorCallback: (errorCallback: any) => any) {
  axios.post(baseUrl + "cards/" + id + "/pay", payload, getConfig())
    .then(res => successCallback(res))
    .catch(err => handleError(err, errorCallback))
}

function handleError(error: any, errorCallback: (errorCallback: any) => any) {
  if (error.response) {
    errorCallback(error.response.data)
  } else {
    errorCallback(error)
  }
}

function getConfig() {
  return { headers: { 'Authorization': 'Bearer ' + localStorage.getItem(USER_TOKEN) } }
}