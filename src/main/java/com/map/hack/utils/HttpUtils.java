package com.map.hack.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Arrays;

public class HttpUtils {
    public static final String BASE_API = "/api/v1/threat";

    public static HttpEntity<String> getDefaultHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        return entity;
    }
}
