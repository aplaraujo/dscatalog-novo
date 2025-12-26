package io.github.aplaraujo.tests;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TokenUtil {
    public String obtainAccessToken(MockMvc mockMvc, String email, String password) throws Exception {

        String authRequestJson = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\"}",
                email,
                password
        );

        System.out.println("=== TENTANDO AUTENTICAR ===");
        System.out.println("JSON enviado: " + authRequestJson);

        ResultActions result = mockMvc
                .perform(post("/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequestJson)
                        .with(csrf()))
                .andDo(print());

        int status = result.andReturn().getResponse().getStatus();
        String body = result.andReturn().getResponse().getContentAsString();

        System.out.println("=== RESPOSTA ===");
        System.out.println("Status: " + status);
        System.out.println("Body: " + body);

        result.andExpect(status().isOk());

        return body;
    }
}
