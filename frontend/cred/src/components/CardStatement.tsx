import { Component } from 'react';
import { Button, Dropdown, DropdownButton, Table } from 'react-bootstrap';
import dayjs from 'dayjs';
import { bindActionCreators } from 'redux';
import { Navbar } from 'react-bootstrap';
import { GetStatementState } from '../models/cardState'
import { RootState } from '../reducers';
import { connect } from "react-redux";
import { getStatement } from '../actions/card-action'
import { getFormattedDate, TransactionType } from '../util/Utils'
import { CustomNavBrand } from './CustomNavBrand'
import { ProgressBar } from 'react-bootstrap';
import SimpleBarChart from './SimpleBarChart'

interface Prop {
    match: any
    getStatementState: GetStatementState
    getStatement: (id: number, year: number, month: number) => void
}

interface State {
    cardId: number
    selectedMonth: number,
    selectedYear: number,
    showTable: boolean
}

class CardStatement extends Component<Prop, State> {
    constructor(props: Prop) {
        super(props)
        let today = dayjs()
        let cardId = this.props.match.params.id
        this.state = {
            cardId: cardId,
            selectedMonth: today.month(),
            selectedYear: today.year(),
            showTable: true
        }
    }
    fetchStatement() {
        this.props.getStatement(this.state.cardId, this.state.selectedYear, this.state.selectedMonth + 1)
    }
    componentDidMount() {
        this.fetchStatement()
    }
    selectMonth = (month: number, e: React.ChangeEvent<any>) => {
        e.preventDefault();
        this.setState({
            selectedMonth: month
        })
    }
    selectYear = (year: number, e: React.ChangeEvent<any>) => {
        e.preventDefault();
        let today = dayjs()
        let month = this.state.selectedMonth
        if (year >= today.year() && this.state.selectedMonth > today.month()) {
            month = today.month()
        }
        this.setState({
            selectedYear: year,
            selectedMonth: month
        })
    }
    handleSubmit = (e: React.ChangeEvent<any>) => {
        e.preventDefault();
        this.fetchStatement()
    }
    toggleTableAndGraphView = (e: React.ChangeEvent<any>) => {
        e.preventDefault();
        const prevState = this.state.showTable
        this.setState({
            showTable: !prevState
        })
    }
    render() {
        const monthNames = ["January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        ];
        let months = [];
        const currentDate = dayjs()
        const currentMonth = currentDate.month()
        const currentYear = currentDate.year()
        for (var i = 0; i < monthNames.length; i++) {
            const disabled = this.state.selectedYear >= currentYear && i > currentMonth
            if (i === this.state.selectedMonth) {
                months.push(<Dropdown.Item key={i.toString()}
                    disabled={disabled}
                    active>{monthNames[i]}
                </Dropdown.Item>);
            } else {
                months.push(
                    <Dropdown.Item key={i.toString()}
                        disabled={disabled}
                        onClick={this.selectMonth.bind(this, i)}>
                        {monthNames[i]}
                    </Dropdown.Item>
                );
            }
        }
        let years = [];
        let today = dayjs()
        for (var j = today.year() - 10; j <= today.year(); j++) {
            if (j === this.state.selectedYear) {
                years.push(<Dropdown.Item active>{j}</Dropdown.Item>);
            } else {
                years.push(<Dropdown.Item onClick={this.selectYear.bind(this, j)}>{j}</Dropdown.Item>);
            }
        }
        let transactions = this.props.getStatementState.statement.map(item => {
            return (<tr>
                <td>{getFormattedDate(item.transactionDate)}</td>
                <td>{item.transactionId}</td>
                <td>{item.vendor}</td>
                <td className={item.transactionType === TransactionType.DEBIT ? "style-debit" : "style-credit"}>{item.currency + " " + item.amount}</td>
            </tr>)
        })
        return (
            <div>
                <Navbar expand="lg" sticky="top" variant="light">
                    <CustomNavBrand />
                </Navbar>
                <div className="statement-container">
                    <div className="statement-input">
                        <div className="statement-heading">Select year and month to view statement</div>
                        <DropdownButton variant="secondary" title={this.state.selectedYear}>
                            {years}
                        </DropdownButton>
                        <DropdownButton variant="secondary" title={monthNames[this.state.selectedMonth]}>
                            {months}
                        </DropdownButton>
                        <Button
                            variant="primary"
                            onClick={this.handleSubmit.bind(this)}>
                            Submit
                        </Button>
                        <Button
                            variant="info"
                            onClick={this.toggleTableAndGraphView.bind(this)}>
                            {this.state.showTable ? "Show graph" : "Show table"}
                        </Button>
                    </div>
                    {this.props.getStatementState.inProgress &&
                        <ProgressBar className="progressbar" animated now={100} />}
                    {this.props.getStatementState.statement.length > 0 ?
                        this.state.showTable ?
                            <Table striped bordered hover size="md" variant="dark">
                                <thead>
                                    <tr>
                                        <th>Date</th>
                                        <th>Transaction id</th>
                                        <th>Vendor</th>
                                        <th>Amount</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {transactions}
                                </tbody>
                            </Table>
                            :
                            <div className="graph-container">
                                <div className="category-graph">
                                    <SimpleBarChart axisName="category" barName="amount"
                                        data={this.props.getStatementState.smartStatemenyByCategory} />
                                    <div className="graph-info-text">Spend by category</div>
                                </div>
                                <div className="vendor-graph">
                                    <SimpleBarChart axisName="vendor" barName="amount"
                                        data={this.props.getStatementState.smartStatementByVendor} />
                                    <div className="graph-info-text">Spend by vendor</div>
                                </div>
                            </div>
                        : <div className="statement-unavailable"> Statement not available </div>
                    }
                </div>
            </div>
        )
    }
}
const mapStateToProps = (state: RootState) => {
    return {
        getStatementState: state.cardReducer.getStatementState
    };
};
const mapDispatchToProps = (dispatch: any) => {
    return {
        ...bindActionCreators({ getStatement }, dispatch)
    };
};
export default connect(mapStateToProps, mapDispatchToProps)(CardStatement)