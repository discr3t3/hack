package com.map.hack.api.v1.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.map.hack.BaseTest;
import com.map.hack.api.v1.type.AbusedIP;
import com.map.hack.api.v1.type.IP;
import com.map.hack.api.v1.type.Location;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;

public class TypeConverterIT extends BaseTest {

    @Value("${test.json.base}")
    private String baseJSON;

    @Autowired
    private TypeConverter typeConverter;

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    public void convertAbusedIPTest() throws IOException {
        Assert.assertNull(this.typeConverter.convert(null));
        AbusedIP convert = this.fileToAbusedIP(baseJSON + "abused_ip.json");
        IP test = this.typeConverter.convert(convert);
        Assert.assertEquals("1.186.219.13", test.getAddress());
        Assert.assertEquals(false, test.isWhiteListed());
        Assert.assertEquals(new HashSet<>(Arrays.asList(14)), test.getCategories());
        Assert.assertEquals((Integer) 16, test.getAbuseConfidenceScore());
        Assert.assertEquals( "11 Oct 2018 03:41:16 GMT", test.getDateCreated().toGMTString());
        Location location = test.getLocation();
        Assert.assertEquals(18.975, location.getLatitude(), .1);
        Assert.assertEquals(72.8258, location.getLongitude(), .1);
        Assert.assertEquals("Mumbai", location.getCity());
        Assert.assertEquals("India", location.getCountry());
        Assert.assertEquals("IN", location.getIsoCode());
    }

    private AbusedIP fileToAbusedIP(String fileName) throws  IOException {
        Resource resource = this.resourceLoader.getResource("classpath:" + fileName);
        String result = new String(Files.readAllBytes(resource.getFile().toPath()));
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(result, AbusedIP.class);
    }
}
