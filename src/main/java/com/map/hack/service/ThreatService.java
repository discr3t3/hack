package com.map.hack.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.map.hack.api.v1.type.AbusedIP;
import com.map.hack.api.v1.type.IP;
import com.map.hack.api.v1.type.Location;
import com.map.hack.utils.BadRequestException;
import com.map.hack.utils.HttpUtils;
import com.map.hack.utils.ResourceNotFoundException;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@EnableCaching
@Service
public class ThreatService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreatService.class);
    public static final String UTF8_ENCODING = "UTF-8";
    public static final String BAD_GUYS_FORMAT = "bad-guys-%1$tY-%1$tm-%1$td.tmp";
    private RestTemplate restTemplate;
    private DatabaseReader databaseReader;
    private ObjectMapper objectMapper;
    private static final TypeReference<List<AbusedIP>> ABUSED_IP = new TypeReference<List<AbusedIP>>(){};
    private static final String ABUSED_ERROR_MAPPING = "Could not Map Object to AbusedIP with Error: %s";

    @Value("${tmp.file.storage}")
    private String baseTmpFile;

    @Value("${ci.badguys.url}")
    private String badGuysUrl;

    @Value("${abuse.api.url}")
    private String abuseApiUrl;

    @Value("${abuse.api.key}")
    private String abuseApiKey;

    @Value("${threat.precache.enabled}")
    private boolean threatCacheEnabled;

    public ThreatService(DatabaseReader databaseReader, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.databaseReader = databaseReader;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Cacheable("allThreats")
    public Set<IP> getRecentThreats() {
        LOGGER.info("Downloading Bad-Guys List: {}", badGuysUrl);

        Set<IP> ips = new LinkedHashSet<>();
        try {
            final String fileName = String.format(BAD_GUYS_FORMAT, new Date());
            final File output = new File(baseTmpFile + fileName);
            BufferedWriter bw = new BufferedWriter(new FileWriter(output));
            //Write to tmpFile, to save output for historical review (only once every 24h currently)

            final URL url = new URL(badGuysUrl);
            InputStream stream = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(stream, UTF8_ENCODING));
            for (String ipAddress; (ipAddress = br.readLine()) != null;) {
                ipAddress = ipAddress.trim();

                if (StringUtils.isEmpty(ipAddress)) {
                    continue;
                }

                bw.write(ipAddress + "\n");
                ips.add(new IP(ipAddress, this.getIpLocation(ipAddress)));
            }

            br.close();
            bw.close();
            stream.close();
        } catch (IOException exception) {
            throw new ResourceNotFoundException(badGuysUrl);
        }

        return ips;
    }

    @Cacheable("threatInfo")
    public List<AbusedIP> getIpInfo(String ipAddress, int totalDays) {
        if (StringUtils.isEmpty(ipAddress)) {
            throw new BadRequestException("IP Address cannot be empty.");
        } else if (totalDays <= 0) {
            throw new BadRequestException("Total Days must be > 0.");
        }

        final String url = String.format(abuseApiUrl, ipAddress, abuseApiKey, totalDays);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET,
                HttpUtils.getDefaultHeader(), String.class
            );
            return this.formatAbusedResponse(response.getBody());
        } catch (Exception exception) {
           LOGGER.info(ResourceNotFoundException.formatMessage(ipAddress, exception));
        }

        return new ArrayList<>();
    }

    public Location getIpLocation(String ipAddress) {
        if (StringUtils.isEmpty(ipAddress)) {
            return null;
        }

        try {
            final InetAddress address = InetAddress.getByName(ipAddress);
            final CityResponse response = this.databaseReader.city(address);
            return new Location(
                response.getLocation().getLatitude(),
                response.getLocation().getLongitude(),
                response.getCity().getName(),
                response.getCountry().getName(),
                response.getCountry().getIsoCode()
            );
        } catch (Exception exception) {
            //GeoIp2Exception, IOException, log and move on.
            LOGGER.info(ResourceNotFoundException.formatMessage(ipAddress, exception));
        }

        return null;
    }

    private List<AbusedIP> formatAbusedResponse(String response) {
        //AbusedIP will return an object or a list, adjust here accordingly.
        if (StringUtils.isEmpty(response)) {
            return new ArrayList<>();
        }

        if (response.startsWith("{")) {
            response = "[" + response + "]";
        }

        try {
            return this.objectMapper.readValue(response, ABUSED_IP);
        } catch (Exception error) {
            //IOException, JsonParseException, JsonMappingException
            LOGGER.info(String.format(ABUSED_ERROR_MAPPING, error.toString()));
        }

        return new ArrayList<>();
    }

    @PostConstruct
    public void load() { //Pre-cache on start-up;
        if (this.threatCacheEnabled) {
            this.getRecentThreats();
        }
    }
}
