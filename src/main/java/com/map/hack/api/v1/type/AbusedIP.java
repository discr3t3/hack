package com.map.hack.api.v1.type;

import java.util.Date;
import java.util.Set;

public class AbusedIP {
    private String ip;
    private Set<Integer> category;
    private Date created;
    private String country;
    private String isoCode;
    private Boolean isWhitelisted;
    private Integer abuseConfidenceScore;

    public AbusedIP() {
    }

    public AbusedIP(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Set<Integer> getCategory() {
        return this.category;
    }

    public void setCategory(Set<Integer> category) {
        this.category = category;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIsoCode() {
        return this.isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public Boolean getIsWhitelisted() {
        return this.isWhitelisted;
    }

    public void setIsWhitelisted(Boolean isWhitelisted) {
        this.isWhitelisted = isWhitelisted;
    }

    public Integer getAbuseConfidenceScore() {
        return this.abuseConfidenceScore;
    }

    public void setAbuseConfidenceScore(Integer abuseConfidenceScore) {
        this.abuseConfidenceScore = abuseConfidenceScore;
    }
}
