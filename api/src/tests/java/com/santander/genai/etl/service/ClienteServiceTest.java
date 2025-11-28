package com.santander.genai.etl.service;

import com.santander.genai.etl.dto.ClienteDTO;
import com.santander.genai.etl.mapper.DtoMapper;
import com.santander.genai.etl.domain.Cliente;
import com.santander.genai.etl.repository.ClienteRepository;
import com.santander.genai.etl.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

public class ClienteServiceTest {

    @Test
    void criarCliente_ok() {
        ClienteRepository repo = Mockito.mock(ClienteRepository.class);
        DtoMapper mapper = new DtoMapper();
        ClienteService service = new ClienteServiceImpl(repo, mapper);

        ClienteDTO dto = new ClienteDTO();
        dto.setNome("Maria");
        dto.setEmail("maria@santander.com");
        dto.setSegmento("Varejo");
        dto.setLimiteCredito(5000);
        dto.setScore(750);

        Mockito.when(repo.save(Mockito.any(Cliente.class))).thenAnswer(inv -> {
            Cliente c = inv.getArgument(0);
            c.setId(1L);
            return c;
        });

        ClienteDTO criado = service.criar(dto);
        assertThat(criado.getId()).isEqualTo(1L);
        assertThat(criado.getNome()).isEqualTo("Maria");
    }
}
