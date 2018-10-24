General Layout:

This is a Spring Boot Application that is coupled with a React front-end. 

Implements:
- Caffeine Caching.
- Geo IP DB.

Purpose: Map IP Addresses from supposed bad actors, and provide additional IP information and context on the 
bad actor.

DataSources used:

- http://cinsscore.com/list/ci-badguys.txt
- https://www.abuseipdb.com/api.html


To Run This Application:
1. mvn spring:bootrun
- This will also initialize and build the npm module/FE access point.

To Unit Test this Application:
1. mvn clean test

To Integration Test this Application:
1. mvn clean verify

*Note: You will need an API key for Google Maps and an API key for the abuseipdb.
       
General Functionality:
- Pull back all of the bad IP (15000), and cache the response locally. Also keep a copy of the file.
- Geocode each of the IP into corresponding locations.
- Map locations on UI.
- Filter locations based on search preferences.
- On Click of Marker/Location, present additional information about the IP via an api call to threat info endpoint.


TODO:
- Use Gradle instead of Maven.
- Implement Persistence Layer.
- Add additional information/API for tracking bad actor IP(s).

