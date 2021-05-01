export interface Transaction {
    transactionDate : string,
	vendor : string,
	category : string,
	transactionId : string,
	amount : number,
	transactionType : string,
	currency: string
}

export interface CategoryData {
	category: string,
	amount: number
}

export interface VendorData {
	vendor: string,
	amount: number,
	count: number
}