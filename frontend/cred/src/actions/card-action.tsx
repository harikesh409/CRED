import { FETCH_CARDS_PROGRESS, FETCH_CARDS_SUCCESS, FETCH_CARDS_FAILED } from "../constants/action-types";
import { ADD_CARD_PROGRESS, ADD_CARD_SUCCESS, ADD_CARD_FAILED } from "../constants/action-types";
import { GET_STATEMENT_SUCCESS, GET_STATEMENT_FAILED, GET_STATEMENT_PROGRESS } from "../constants/action-types";
import { PAY_BILL_SUCCESS, PAY_BILL_FAILED, PAY_BILL_PROGRESS } from "../constants/action-types";
import { getAllCardsApi, addCardApi, getStatementApi, payBillApi } from '../services/card-service'

export function getAllCards() {
    return function (dispatch:any) {
        dispatch({type:FETCH_CARDS_PROGRESS, payload:""})
        getAllCardsApi((response: any) => {
            dispatch({type:FETCH_CARDS_SUCCESS, payload:response})
        }, (error:any) => {
            dispatch({type:FETCH_CARDS_FAILED, payload:error.message})
        })
    }
}

export function addCard(payload: any) {
    return function (dispatch:any) {
        dispatch({type:ADD_CARD_PROGRESS, payload:""})
        addCardApi(payload, (response: any) => {
            dispatch({type:ADD_CARD_SUCCESS, payload:response})
        }, (error:any) => {
            dispatch({type:ADD_CARD_FAILED, payload:error.message})
        })
    }
}

export function getStatement(id: number, year: number, month: number) {
    return function (dispatch:any) {
        dispatch({type:GET_STATEMENT_PROGRESS, payload:""})
        getStatementApi(id, year, month, (response: any) => {
            dispatch({type:GET_STATEMENT_SUCCESS, payload:response})
        }, (error:any) => {
            dispatch({type:GET_STATEMENT_FAILED, payload:error.message})
        })
    }
}

export function payBill(id: string, payload: any) {
    return function (dispatch:any) {
        dispatch({type:PAY_BILL_PROGRESS, payload:""})
        payBillApi(id, payload, (response: any) => {
            dispatch({type:PAY_BILL_SUCCESS, payload:response})
        }, (error:any) => {
            dispatch({type:PAY_BILL_FAILED, payload:error.message})
        })
    }
}