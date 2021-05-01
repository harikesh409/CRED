import { FETCH_CARDS_PROGRESS, FETCH_CARDS_SUCCESS, FETCH_CARDS_FAILED } from "../constants/action-types";
import { ADD_CARD_PROGRESS, ADD_CARD_SUCCESS, ADD_CARD_FAILED } from "../constants/action-types";
import { GET_STATEMENT_SUCCESS, GET_STATEMENT_FAILED, GET_STATEMENT_PROGRESS } from "../constants/action-types";
import { PAY_BILL_SUCCESS, PAY_BILL_FAILED, PAY_BILL_PROGRESS } from "../constants/action-types";
import { GetAllCardsState, AddCardState, GetStatementState, PayBillState } from '../models/cardState';

const defaultGetAllCardsState: GetAllCardsState = {
  inProgress: false,
  success: false,
  error: "",
  cards: []
}

const defaultAddCardState: AddCardState = {
  inProgress: false,
  success: false,
  error: ""
}

const defaultGetStatementState: GetStatementState = {
  inProgress: false,
  success: false,
  error: "",
  statement: [],
  smartStatemenyByCategory: [],
  smartStatementByVendor: []
}

const defaultPayBillState: PayBillState = {
  inProgress: false,
  success: false,
  error: ""
}

const defaultState = {
  getAllCardsState: defaultGetAllCardsState,
  addCardState: defaultAddCardState,
  getStatementState: defaultGetStatementState,
  payBillState: defaultPayBillState
}

function cardReducer(state = defaultState, action: any) {
  switch (action.type) {
    case FETCH_CARDS_PROGRESS: {
      const newState = {
        ...state.getAllCardsState
      }
      newState.inProgress = true
      newState.success = false
      newState.error = ""
      newState.cards = []
      return {
        ...state,
        getAllCardsState: newState,
        payBillState: defaultPayBillState
      }
    }
    case FETCH_CARDS_SUCCESS: {
      const newState = {
        ...state.getAllCardsState
      }
      newState.inProgress = false
      newState.success = true
      newState.error = ""
      newState.cards = action.payload.data.content
      return {
        ...state,
        getAllCardsState: newState
      }
    }
    case FETCH_CARDS_FAILED: {
      const newState = {
        ...state.getAllCardsState
      }
      newState.inProgress = false
      newState.success = false
      newState.error = action.payload
      newState.cards = []
      return {
        ...state,
        getAllCardsState: newState
      }
    }
    case ADD_CARD_PROGRESS: {
      const newState = {
        ...state.addCardState
      }
      newState.inProgress = true
      newState.success = false
      newState.error = ""
      return {
        ...state,
        addCardState: newState
      }
    }
    case ADD_CARD_SUCCESS: {
      const newState = {
        ...state.addCardState
      }
      newState.inProgress = false
      newState.success = true
      newState.error = ""
      return {
        ...state,
        addCardState: newState
      }
    }
    case ADD_CARD_FAILED: {
      const newState = {
        ...state.addCardState
      }
      newState.inProgress = false
      newState.success = false
      newState.error = action.payload
      return {
        ...state,
        addCardState: newState
      }
    }
    case GET_STATEMENT_PROGRESS: {
      const newState = {
        ...state.getStatementState
      }
      newState.inProgress = true
      newState.success = false
      newState.error = ""
      return {
        ...state,
        getStatementState: newState
      }
    }
    case GET_STATEMENT_SUCCESS: {
      const newState = {
        ...state.getStatementState
      }
      newState.inProgress = false
      newState.success = true
      newState.error = ""
      newState.statement = action.payload[0].data.content
      newState.smartStatemenyByCategory = action.payload[1].data
      newState.smartStatementByVendor = action.payload[2].data
      console.log("PAYLOAD = " + action.payload.data)
      return {
        ...state,
        getStatementState: newState
      }
    }
    case GET_STATEMENT_FAILED: {
      const newState = {
        ...state.getStatementState
      }
      newState.inProgress = false
      newState.success = false
      newState.error = action.payload
      return {
        ...state,
        getStatementState: newState
      }
    }
    case PAY_BILL_PROGRESS: {
      const newState = {
        ...state.payBillState
      }
      newState.inProgress = true
      newState.success = false
      newState.error = ""
      return {
        ...state,
        payBillState: newState
      }
    }
    case PAY_BILL_SUCCESS: {
      const newState = {
        ...state.payBillState
      }
      newState.inProgress = false
      newState.success = true
      newState.error = ""
      return {
        ...state,
        payBillState: newState
      }
    }
    case PAY_BILL_FAILED: {
      const newState = {
        ...state.payBillState
      }
      newState.inProgress = false
      newState.success = false
      newState.error = action.payload
      return {
        ...state,
        payBillState: newState
      }
    }
    default:
      return state;
  }
}

export default cardReducer;