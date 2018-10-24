package com.map.hack.api.v1.controller;

import com.map.hack.BaseControllerTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.anything;

import static com.map.hack.api.v1.controller.ThreatController.ALL_ENDPOINT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static com.map.hack.utils.HttpUtils.BASE_API;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ThreatControllerIT extends BaseControllerTest {

    @Test
    public void getThreatsTest() throws Exception {
        this.mockMvc.perform(get(BASE_API + ALL_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.count", anything()))
                .andExpect(jsonPath("$.results", anything()));
    }

    @Test
    public void getThreatReportTest() throws Exception {
        this.mockMvc.perform(get(BASE_API + "/127.0.0.1")
                .param("days", "-1"))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(get(BASE_API + "/1.186.219.13")
                .param("days", "7"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.count", anything()))
                .andExpect(jsonPath("$.results", anything()));
    }
}
