import React, { Component } from 'react';
import { Navbar } from 'react-bootstrap';
import { connect } from 'react-redux';
import { addCard } from '../actions/card-action'
import { bindActionCreators } from 'redux';
import { RootState } from '../reducers';
import { AddCardState } from '../models/cardState';
import { FormState } from '../models/formState';
import { isCardNumberValid, isCardCvvValid, isCardExpiryValid, getCardType } from '../util/Utils'
import { ProgressBar } from 'react-bootstrap';
import { CustomNavBrand } from './CustomNavBrand'
import "../styles/login.css"

interface AddCardProp {
    addCardState: AddCardState,
    history: any,
    addCard: (payload: any) => void
}

const CARD_NUMBER = "card_number"
const CVV = "cvv"
const EXPIRY = "expiry"
const NAME = "name"
const CARD_NICK_NAME = "card_nick_name"

class AddNewCard extends Component<AddCardProp, FormState> {
    constructor(props: AddCardProp) {
        super(props)
        this.state = {
            fields: {},
            errors: {},
        }
    }
    handleChange(field: string, e: React.ChangeEvent<HTMLInputElement>) {
        let fields = this.state.fields;
        fields[field] = e.target.value;
        this.setState({ fields });
        this.setState({ errors: {} });
    }
    handleSubmit = (e: React.ChangeEvent<any>) => {
        e.preventDefault()
        if (!this.isFormValid()) {
            return
        }
        const payload = {
            cardNumber: this.state.fields[CARD_NUMBER],
            cvv: this.state.fields[CVV],
            expiryDate: this.state.fields[EXPIRY],
            nameOnCard: this.state.fields[NAME],
            cardPaymentService: getCardType(this.state.fields[CARD_NUMBER]),
            cardNickName: this.state.fields[CARD_NICK_NAME],
        };
        this.props.addCard(payload)
    }
    isFormValid = () => {
        let fields = this.state.fields;
        let errors = this.state.errors;
        let formValid = true;
        if (!isCardNumberValid(fields[CARD_NUMBER])) {
            errors[CARD_NUMBER] = "Invalid card";
            formValid = false;
        } else if (!isCardCvvValid(fields[CVV])) {
            errors[CVV] = "Invalid CVV";
            formValid = false;
        } else if (!isCardExpiryValid(fields[EXPIRY])) {
            errors[EXPIRY] = "Invalid Expiry";
            formValid = false;
        } else if (!fields[NAME]) {
            errors[NAME] = "Name cannot be empty";
            formValid = false;
        } else if (!fields[CARD_NICK_NAME]) {
            errors[CARD_NICK_NAME] = "Card Nick Name cannot be empty";
            formValid = false;
        }
        if (!formValid) {
            this.setState({ errors });
        } else {
            this.setState({ errors: {} });
        }
        return formValid;
    }
    render() {
        if (this.props.addCardState.success) {
            this.props.history.replace('/')
        }
        return (
            <div>
                <Navbar expand="lg" sticky="top" variant="light">
                    <CustomNavBrand/>
                </Navbar>
                <form className="form">
                    <h4 className="login-form-heading">Add a card</h4>
                    <div className="form-group">
                        <label>Card Nick Name</label>
                        <input
                            type="text"
                            className="form-control"
                            placeholder="like - my shopping card"
                            onChange={this.handleChange.bind(this, CARD_NICK_NAME)}
                        />
                        <span className="error-message">
                            {this.state.errors[CARD_NICK_NAME]}
                        </span>
                    </div>
                    <div className="form-group">
                        <label>Card number</label>
                        <input
                            type="number"
                            className="form-control"
                            placeholder="Enter card number"
                            onChange={this.handleChange.bind(this, CARD_NUMBER)}
                        />
                        <span className="error-message">
                            {this.state.errors[CARD_NUMBER]}
                        </span>
                    </div>
                    <div className="form-group">
                        <label>CVV</label>
                        <input
                            type="password"
                            className="form-control"
                            placeholder="Enter CVV"
                            onChange={this.handleChange.bind(this, CVV)}
                        />
                        <span className="error-message">{this.state.errors[CVV]}</span>
                    </div>
                    <div className="form-group">
                        <label>Expiry</label>
                        <input
                            type="text"
                            className="form-control"
                            placeholder="Enter expiry(MM/YYYY)"
                            onChange={this.handleChange.bind(this, EXPIRY)}
                        />
                        <span className="error-message">
                            {this.state.errors[EXPIRY]}
                        </span>
                    </div>
                    <div className="form-group">
                        <label>Name</label>
                        <input
                            type="text"
                            className="form-control"
                            placeholder="Enter name on the card"
                            onChange={this.handleChange.bind(this, NAME)}
                        />
                        <span className="error-message">{this.state.errors[NAME]}</span>
                    </div>
                    <button
                        type="submit"
                        className="btn btn-primary btn-block"
                        disabled={this.props.addCardState.inProgress}
                        onClick={this.handleSubmit.bind(this)}
                    >
                        Add Card
              </button>
                    {this.props.addCardState.inProgress ? (
                        <ProgressBar className="progressbar" animated now={100} />
                    ) : (
                        ""
                    )}
                    {this.props.addCardState.error ? (
                        <span className="row d-flex justify-content-center error-message">
                            {this.props.addCardState.error}
                        </span>
                    ) : (
                        ""
                    )}
                </form>
            </div>
        );
    }
}
const mapStateToProps = (state: RootState) => {
    return { addCardState: state.cardReducer.addCardState };
};
const mapDispatchToProps = (dispatch: any) => {
    return {
        ...bindActionCreators({ addCard }, dispatch)
    };
};
export default connect(mapStateToProps, mapDispatchToProps)(AddNewCard);