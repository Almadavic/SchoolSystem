package application.integrationTests.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.net.URI;

@SpringBootTest
@AutoConfigureMockMvc
@Profile(value = "test")
class AuthenticationControllerTest { // TESTE DE INTEGRAÇÃO que testa a autenticação do usuário.

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void incorrectData() throws Exception { // Testa quando são passados dados incorretos.

        URI uri = new URI("/auth");

        String json = "{\"email\":\"invalido@email.com\",\"senha\":\"123456}\"";

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().is(400));
    }

}