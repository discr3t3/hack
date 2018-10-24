package com.map.hack.service;

import com.map.hack.BaseTest;
import com.map.hack.api.v1.type.AbusedIP;
import com.map.hack.api.v1.type.IP;
import com.map.hack.api.v1.type.Location;
import com.map.hack.utils.BadRequestException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class ThreatServiceIT extends BaseTest {

    @Value("${tmp.file.storage}")
    private String baseTmpFile;

    @Autowired
    private ThreatService threatService;

    @Test
    public void getRecentThreatsTest() {
        Set<IP> ips = this.threatService.getRecentThreats();
        Assert.assertEquals(15000, ips.size());
        ips.stream().forEach(testIp -> {
            Assert.assertTrue(!StringUtils.isEmpty(testIp.getAddress()));
            Location location = testIp.getLocation();
            if (location != null) {
                //Lat & Long are guaranteed, no other properties should be AFAIK;
                Assert.assertNotNull(location.getLatitude());
                Assert.assertNotNull(location.getLatitude());
            }
        });

        final String fileName = String.format(ThreatService.BAD_GUYS_FORMAT, new Date());
        File f = new File(baseTmpFile + fileName);
        Assert.assertTrue(f.exists());
        f.delete();
    }

    @Test
    public void getIpInfoTest() {
        try {
            this.threatService.getIpInfo(null, 7);
        } catch (BadRequestException exception)  {
            Assert.assertEquals("IP Address cannot be empty.", exception.getMessage());
        }

        try {
            this.threatService.getIpInfo("127.0.1.1", -1);
        } catch (BadRequestException exception)  {
            Assert.assertEquals("Total Days must be > 0.", exception.getMessage());
        }

        List<AbusedIP> ips = this.threatService.getIpInfo("1.186.219.13", 7);
        ips.forEach(ip -> Assert.assertEquals("1.186.219.13", ip.getIp()));
    }

    @Test
    public void getIpLocation() {
        Assert.assertNull(this.threatService.getIpLocation(null));

        Location location = this.threatService.getIpLocation("1.186.219.13");
        Assert.assertEquals(18.975, location.getLatitude(), .1);
        Assert.assertEquals(72.8258, location.getLongitude(), .1);
        Assert.assertEquals("Mumbai", location.getCity());
        Assert.assertEquals("India", location.getCountry());
        Assert.assertEquals("IN", location.getIsoCode());
    }
}
