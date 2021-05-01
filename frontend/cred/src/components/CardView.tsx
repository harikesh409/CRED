import { Component } from "react";
import { Card } from "react-bootstrap";
import { CreditCard } from '../models/creditCard'
import { getFormattedDate } from '../util/Utils'

interface Prop {
    creditCard: CreditCard,
    showPaybillModal: (creditCard: CreditCard) => void
}

interface State {
    flipped: boolean
}
class CardView extends Component<Prop, State> {
    constructor(prop: Prop) {
        super(prop)
        this.state = {
            flipped: false
        }
    }
    handleClick = (e: React.ChangeEvent<any>) => {
        let oldState = this.state.flipped
        this.setState({
            flipped: !oldState
        })
    }
    render() {
        let card = this.props.creditCard
        return (
            <Card border="dark" className="card">
                <Card.Body className='card-body' onClick={this.handleClick.bind(this)}>
                    {!this.state.flipped ?
                        <div className="card_front">
                            <div className='card-title'>{card.cardNickName}</div>
                            <div className='card-number'>{card.cardNumber}</div>
                            <div className='card--footer'>
                                <div className='name-on-card'>{card.nameOnCard}</div>
                                <div className='card-expiry'>{card.expiryDate}</div>
                            </div>
                        </div>
                        :
                        <div className="card_back">
                            <div className='due-details'>{"Min due: " + card.minDue}</div>
                            <div className='due-details'>{"Total due: " + card.totalDue}</div>
                            <div className='due-details'>{"Due date: " + getFormattedDate(card.dueDate)}</div>
                            <div className='card--footer'>
                                <a className='card-link' href={"/view-statement/" + card.cardId}>
                                        View Statement
                                </a>
                                {card.totalDue > 0 ?
                                    <div className='card-link' onClick={() => this.props.showPaybillModal(card)}>Pay bill</div> :
                                    <div className='card-link-disabled'>Pay bill</div>}
                            </div>
                        </div>
                    }
                </Card.Body>
            </Card>
        )
    }
}

export default CardView