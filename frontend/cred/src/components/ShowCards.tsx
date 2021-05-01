import { Component } from "react";
import { Container, Col, Row } from "react-bootstrap";
import { Navbar, Button, Nav } from 'react-bootstrap';
import { connect } from "react-redux";
import { bindActionCreators } from 'redux';
import { GetAllCardsState } from "../models/cardState";
import { CreditCard } from "../models/creditCard";
import { RootState } from '../reducers';
import CardView from './CardView'
import { getAllCards } from '../actions/card-action'
import { logout } from '../actions/auth-action'
import '../styles/cards.css'
import { LogoutState } from "../models/authState";
import { CustomNavBrand } from './CustomNavBrand'
import PayBillModal from './PayBillModal'

interface ShowCardsProp {
    getAllCardsState: GetAllCardsState,
    logoutState: LogoutState,
    history: any,
    getAllCards: () => void,
    logout: () => void
}

interface State {
    showModal: boolean,
    currentCard: CreditCard | null
}

class ShowCards extends Component<ShowCardsProp, State> {
    constructor(props: ShowCardsProp) {
        super(props)
        this.state = {
            showModal: false,
            currentCard: null
        }
    }
    componentDidMount() {
        this.props.getAllCards()
    }
    handleSubmit = (e: React.ChangeEvent<any>) => {
        e.preventDefault();
        this.props.logout()
    }
    showPaybillModal = (creditCard: CreditCard) => {
        this.setState({
            showModal: true,
            currentCard: creditCard
        })
    }
    closeModal = () => {
        this.setState({
            showModal: false,
            currentCard: null
        })
        window.location.reload();
    }
    render() {
        if(this.props.logoutState.success) {
            this.props.history.replace('/login')
        }
        let cards = this.props.getAllCardsState.cards
        let cardGrid = cards.reduce(function (result, _, index, array) {
            if (index % 2 === 0) result.push(array.slice(index, index + 2));
            return result;
        }, new Array<Array<CreditCard>>());
        let cardsView = cardGrid.map(items => {
            let rows = []
            let cols = items.map(card => {
                return (<Col className="card-column">{
                    <CardView creditCard = {card} showPaybillModal={this.showPaybillModal}/>}
                </Col>)
            })
            rows.push(<Row style={{ marginTop: '36px' }}>{cols}</Row>)
            return rows
        })
        return (
            <div>
                <Navbar expand="lg" sticky="top" variant="light">
                    <CustomNavBrand/>
                    <Nav className="ml-auto">
                        <Button className="add-card-button" variant="outline-primary" href="/add-card">
                            Add a card
                        </Button>
                        <Button 
                            variant="outline-danger"
                            onClick={this.handleSubmit.bind(this)}>
                            Log out
                        </Button>
                    </Nav>
                </Navbar>
                {this.props.getAllCardsState.cards.length > 0 ? <Container className="cards-container">
                    {cardsView}
                </Container> : 
                <div className="no-cards_available">
                    {this.props.getAllCardsState.inProgress ? "" : "No cards available"}
                </div>}
                {this.state.showModal && <PayBillModal creditCard = {this.state.currentCard!} 
                hideModal={this.closeModal}/>}
            </div>
        )
    }
}
const mapStateToProps = (state: RootState) => {
    return { 
        getAllCardsState: state.cardReducer.getAllCardsState,
        logoutState: state.authReducer.logoutState
    };
};
const mapDispatchToProps = (dispatch: any) => {
    return {
        ...bindActionCreators({ getAllCards, logout }, dispatch)
    };
};
export default connect(mapStateToProps, mapDispatchToProps)(ShowCards)