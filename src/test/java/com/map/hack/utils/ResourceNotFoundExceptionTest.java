package com.map.hack.utils;

import com.map.hack.BaseTest;
import org.junit.Assert;
import org.junit.Test;

public class ResourceNotFoundExceptionTest extends BaseTest {
    private static final String TEST = "test";

    @Test
    public void messageTest() {
        ResourceNotFoundException exception = new ResourceNotFoundException(TEST);
        Assert.assertEquals("Could not access Resource: test.", exception.getMessage());

        final Exception testException = new Exception();
        exception = new ResourceNotFoundException(TEST, testException);
        final String expected = "Could not access Resource: test with exception: " + testException.toString();
        Assert.assertEquals(expected, exception.getMessage());
    }

    @Test
    public void formatMessageTest() {
        final String expectedMessage = "Could not access Resource: test.";
        final String message = ResourceNotFoundException.formatMessage(TEST);
        Assert.assertEquals(expectedMessage, message);
    }

    @Test
    public void formatMessageExceptionTest() {
        final Exception exception = new Exception();
        final String expectedMessage = "Could not access Resource: test with exception: " + exception.toString();
        final String message = ResourceNotFoundException.formatMessage(TEST, exception);
        Assert.assertEquals(expectedMessage, message);
    }
}
