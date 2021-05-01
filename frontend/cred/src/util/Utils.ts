import { isValid } from 'cc-validate'
import dayjs from 'dayjs';

const emailRegex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}"
const numberRegex = '^[0-9]*$'

export function isEmailValid(email: string) {
   return email && email.match(emailRegex)
}

export function isMobileNumberValid(mobileNumber: string) {
   return mobileNumber && isNumber(mobileNumber) && mobileNumber.length === 10
}

export function isCardNumberValid(cardNumber: string) {
   let result = isValid(cardNumber)
   return (result as any).isValid
}

export function isCardCvvValid(cardCvv: string) {
   return cardCvv && isNumber(cardCvv)
}

export function isCardExpiryValid(cardExpiry: string) {
   if(!cardExpiry) return false
   let splitarr = cardExpiry.split('/')
   let today = dayjs(new Date())
   let expiry = dayjs(splitarr[1] + "-" + splitarr[0] + "-01")
   return expiry.isAfter(today)
}

function isNumber(value: string) {
   return value.match(numberRegex)
}

export function getCardType(cardNumber: string) {
   let result = isValid(cardNumber)
   return (result as any).cardType.toUpperCase()
}

export function getFormattedDate(date: string) {
   return dayjs(date).format('DD/MM/YYYY')
}

export enum TransactionType {
   DEBIT = "DEBIT", 
   CREDIT = "CREDIT"
}