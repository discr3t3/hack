import React, { Component } from "react";
import IP from "./IP";

class IPList extends Component{

    render() {
        const ipList = this.props.ipList.map(
            ip => <IP key={ip.address} ip={ip}/>
        );
        return (
            <table id="ipTable">
                <tbody>
                <tr>
                    <th>IP Address</th>
                    <th>City</th>
                    <th>Country</th>
                </tr>
                {ipList}
                </tbody>
            </table>
        )
    }
}

export default IPList;