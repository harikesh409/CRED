export interface CreditCard {
    cardId: string,
    cardNumber: number,
    cardNickName: string,
    cardPaymentService: string,
    expiryDate: string,
    nameOnCard: string,
    dueDate: string,
    minDue: number,
    totalDue: number
}