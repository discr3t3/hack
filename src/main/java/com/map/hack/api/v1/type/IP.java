package com.map.hack.api.v1.type;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class IP {
    private String address;
    private Location location;
    private Set categories;
    private Date dateCreated;
    private Boolean isWhiteListed;
    private Integer abuseConfidenceScore;

    public IP(String address, Location location) {
        this.address = address;
        this.location = location;
    }

    public Set getCategories() {
        return this.categories;
    }

    public void setCategories(Set categories) {
        this.categories = categories;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean isWhiteListed() {
        return this.isWhiteListed;
    }

    public void setWhiteListed(Boolean whiteListed) {
        this.isWhiteListed = whiteListed;
    }

    public Integer getAbuseConfidenceScore() {
        return this.abuseConfidenceScore;
    }

    public void setAbuseConfidenceScore(Integer abuseConfidenceScore) {
        this.abuseConfidenceScore = abuseConfidenceScore;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object compare) {
        if (this == compare) {
            return true;
        } else if (compare == null || this.getClass() != compare.getClass()) {
            return false;
        }
        IP against = (IP) compare;
        return Objects.equals(address, against.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
