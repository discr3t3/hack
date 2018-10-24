package com.map.hack.utils;

import com.map.hack.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.Arrays;

public class HttpUtilsTest extends BaseTest {

    @Test
    public void getDefaultHeaderTest() {
        Assert.assertEquals(Arrays.asList(MediaType.APPLICATION_JSON),
                            HttpUtils.getDefaultHeader().getHeaders().getAccept());
    }
}
