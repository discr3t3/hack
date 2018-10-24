package com.map.hack.api.v1.controller;

import com.map.hack.api.v1.converter.TypeConverter;
import com.map.hack.api.v1.type.IP;
import com.map.hack.api.v1.type.SearchResult;
import com.map.hack.service.ThreatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.map.hack.utils.HttpUtils.BASE_API;

@RestController
@RequestMapping(value = BASE_API, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ThreatController {
    public static final String ALL_ENDPOINT = "/all";
    public static final String THREAT_REPORT_ENDPOINT = "/{ip}";

    @Autowired
    private ThreatService threatService;

    @Autowired
    private TypeConverter typeConverter;

    @GetMapping(value = ALL_ENDPOINT)
    public SearchResult<IP> getThreats() {
        return new SearchResult<>(
            new ArrayList<>(this.threatService.getRecentThreats())
        );
    }

    @GetMapping(value = THREAT_REPORT_ENDPOINT)
    public SearchResult<IP> getThreatReport(@PathVariable String ip,
                                    @RequestParam(name = "days", defaultValue = "14", required = false) int totalDays) {
        return new SearchResult<>(
            this.threatService.getIpInfo(ip, totalDays)
            .parallelStream()
            .map(this.typeConverter::convert)
            .collect(Collectors.toList())
        );
    }

    @RequestMapping(path = "/")
    public String index() {
        return "index";
    }
}
