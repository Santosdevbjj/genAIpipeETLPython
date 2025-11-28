package com.santander.genai.etl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.santander.genai.etl.dto.ClienteDTO;
import com.santander.genai.etl.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClienteControllerTest {

    @Test
    void criar_sem_token_deve_403_quando_security_habilitada() throws Exception {
        ClienteService service = Mockito.mock(ClienteService.class);
        ClienteController controller = new ClienteController(service);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        ClienteDTO dto = new ClienteDTO();
        dto.setNome("João");
        dto.setEmail("joao@santander.com");
        dto.setSegmento("Varejo");

        mvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().is4xxClientError());
    }
}
