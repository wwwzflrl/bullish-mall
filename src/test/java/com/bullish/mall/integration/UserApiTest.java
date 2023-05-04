package com.bullish.mall.integration;

import com.bullish.mall.core.user.User;
import com.bullish.mall.core.user.UserRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
public class UserApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void fail_to_login_no_valid_request() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/login")
                        .contentType(APPLICATION_JSON)
                        .content(loginBody(null)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("{\"type\":\"about:blank\",\"title\":\"Bad Request\",\"status\":400,\"detail\":\"Failed to read request\",\"instance\":\"/user/login\"}"));
    }

    @Test
    public void fail_to_login_no_valid_user() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/login")
                .contentType(APPLICATION_JSON)
                .content(loginBody("no user")))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("{\"message\":\"Invalid User Name\"}"));
    }


    private String loginBody(String username) {
        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        return new JSONObject(body).toString();
    }
}
