import React, { Component } from "react";

class IP extends Component{
    render() {
        return (
            <tr>
                <td>{this.props.ip.address}</td>
                {this.props.ip.location &&  this.props.ip.location.city ? <td>{this.props.ip.location.city}</td> : null}
                {this.props.ip.location &&  this.props.ip.location.country ? <td>{this.props.ip.location.country}</td> : null}
            </tr>
        )
    }
}

export default IP;