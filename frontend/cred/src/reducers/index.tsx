import { combineReducers } from "redux";
import authReducer from "./auth-reducer";

import cardReducer from './card-reducer'

export const rootReducer = combineReducers({
    authReducer: authReducer,
    cardReducer: cardReducer,
});
export type RootState = ReturnType<typeof rootReducer>