package com.map.hack.api.v1.converter;

import com.map.hack.api.v1.type.AbusedIP;
import com.map.hack.api.v1.type.IP;
import com.map.hack.service.ThreatService;
import org.springframework.stereotype.Service;

@Service
public class TypeConverter {
    private ThreatService threatService;

    public TypeConverter(ThreatService threatService) {
        this.threatService = threatService;
    }

    public IP convert(AbusedIP convert) {
        if (convert == null) {
            return null;
        }

        IP ip = new IP(convert.getIp(), this.threatService.getIpLocation(convert.getIp()));
        ip.setAbuseConfidenceScore(convert.getAbuseConfidenceScore());
        ip.setCategories(convert.getCategory());
        ip.setDateCreated(convert.getCreated());
        ip.setWhiteListed(convert.getIsWhitelisted());
        ip.setAbuseConfidenceScore(convert.getAbuseConfidenceScore());

        return ip;
    }
}
