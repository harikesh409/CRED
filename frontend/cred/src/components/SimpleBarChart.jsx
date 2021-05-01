import { Component } from 'react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';

export default class SimpleBarChart extends Component {
  render() {
    return (
      <ResponsiveContainer width="100%" height="100%">
        <BarChart
          width={500}
          height={300}
          data={this.props.data}
          margin={{
            top: 5,
            right: 30,
            left: 20,
            bottom: 5,
          }}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey={this.props.axisName} />
          <YAxis />
          <Tooltip />
          <Legend />
          <Bar dataKey={this.props.barName} fill="#454d55" />
        </BarChart>
      </ResponsiveContainer>
    );
  }
}