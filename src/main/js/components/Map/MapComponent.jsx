import React, { Component } from "react";
import { Map, InfoWindow, Marker, GoogleApiWrapper } from "google-maps-react";
import { get } from '../../utils/request';
import IPList from "../IP/IPList";
import Search from "../Search/Search";

const BASE_API = 'http://localhost:8080/api/v1';

export class MapContainer extends Component {
    constructor(props) {
        super(props);
        this.onMarkerClick = this.onMarkerClick.bind(this);
        this.state = {
            showingInfoWindow: false,
            activeMarker: {},
            selectedPlace: {},
            ipData: {},
            ips: [],
            ipList: [],
        };
        get(BASE_API + '/threat/all')
            .then(response => {
                if (response.data && response.data.results) {
                    this.setState({ips: response.data.results, ipList: response.data.results});
                }
            });
    }

    onMarkerClick(props, marker, e) {
        get(BASE_API + '/threat/' + marker.name)
            .then(response => {
                if (response.data && response.data.results) {
                    this.setState({ipData: response.data})
                }
            });
        this.setState({
            selectedPlace: props,
            activeMarker: marker,
            showingInfoWindow: true
        });
    }

    searchIP(query){
        const ipList = this.state.ips.filter((ip) => {
            query = query.toLowerCase();
            return (ip.address && ip.address.toLowerCase().includes(query)) ||
                (ip.location && ip.location.city && ip.location.city.toLowerCase().includes(query)) ||
                (ip.location && ip.location.country && ip.location.country.toLowerCase().includes(query))
        });
        this.setState({ipList: ipList})
    }

    render() {
        if (!this.props.google) {
            return <div>Loading Map Visualization...</div>;
        }

        return (
            <div id="mapContainer">
                <Map id="map" google={this.props.google} zoom={4}>

                    {this.state.ipList.map((ip) => {
                        if (ip.location.latitude && ip.location.longitude) {
                            return <Marker
                                onClick={this.onMarkerClick}
                                key={ip.address}
                                position={{lat: ip.location.latitude, lng: ip.location.longitude}}
                                name={ip.address}
                            />
                        }
                    })
                    }

                    <div id="listContainer">
                        <Search searchIP={this.searchIP.bind(this)}/>
                        <IPList ipList={this.state.ipList} />
                    </div>

                    <InfoWindow
                        marker={this.state.activeMarker}
                        visible={this.state.showingInfoWindow}
                    >
                        <div><pre>{JSON.stringify(this.state.ipData, null, 2) }</pre></div>
                    </InfoWindow>
                </Map>
            </div>
        );
    }
}
export default GoogleApiWrapper({
    apiKey: "APP_API_KEY_HERE",
    v: "3.30"
})(MapContainer);
