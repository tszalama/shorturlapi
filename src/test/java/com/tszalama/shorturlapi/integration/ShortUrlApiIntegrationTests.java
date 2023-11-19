package com.tszalama.shorturlapi.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tszalama.shorturlapi.dto.ShortUrlCreationRequestDTO;
import com.tszalama.shorturlapi.dto.ShortUrlCreationResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class ShortUrlApiIntegrationTests {

    //TODO: Use separate test database instance for testing

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private int testIteration = 0;
    private final String baseTestUrl = "https://www.google.com/search?q=what+is+2+%2B";

    @RepeatedTest(100)
    public void createShortenedUrlAndUseIt() throws Exception {
        //get unique url for each iteration
        String testUrl = baseTestUrl + testIteration;

        //validate urlId creation
        ShortUrlCreationRequestDTO shortUrlRequestDTO = new ShortUrlCreationRequestDTO(testUrl);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shortUrlRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.urlId").exists())
                .andReturn();

        //validate retrieval based on generated url
        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        String createdUrlId = jsonNode.get("urlId").asText();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/" + createdUrlId))
                .andExpect(status().isSeeOther())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.LOCATION, testUrl));

        log.info("Url with id " + createdUrlId + " shortened and validated");

        testIteration++;
    }

    @Test
    public void invalidUrlIdNotFound() {
        //TODO: implement
    }

    @Test
    public void noUrlInRequestBody() {
        //TODO: implement
    }

    @Test
    public void invalidUrlInRequestBody() {
        //TODO: implement
    }
}
