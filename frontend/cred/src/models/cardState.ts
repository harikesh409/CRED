import { CreditCard } from "./creditCard";
import { CategoryData, Transaction, VendorData } from "./transaction";

interface BaseState {
    inProgress: boolean,
    success: boolean,
    error: any
}

export interface GetAllCardsState extends BaseState{
    cards: Array<CreditCard>
}

export interface AddCardState extends BaseState{
}

export interface GetStatementState extends BaseState{
    statement: Array<Transaction>
    smartStatemenyByCategory: Array<CategoryData>
    smartStatementByVendor: Array<VendorData>
}

export interface PayBillState extends BaseState {
    
}