import React, { Component } from 'react';

class Search extends Component {

    handleSearch(event) {
        this.props.searchIP(event.target.value)
    }

    render() {
        return (
            <div className="searchBox">
                <div className="inputField">
                    <label className="inputLabel">Search</label>
                    <input type="text" onKeyUp={this.handleSearch.bind(this)}/>
                </div>
            </div>
        )
    }
}

export default Search;