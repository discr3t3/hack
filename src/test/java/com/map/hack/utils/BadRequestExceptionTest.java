package com.map.hack.utils;

import com.map.hack.BaseTest;
import org.junit.Assert;
import org.junit.Test;

public class BadRequestExceptionTest extends BaseTest {

    @Test
    public void messageTest() {
        final String test = "test";
        final BadRequestException exception = new BadRequestException(test);
        Assert.assertEquals(test, exception.getMessage());
    }
}
