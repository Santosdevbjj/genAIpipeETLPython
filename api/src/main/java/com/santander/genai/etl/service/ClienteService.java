package com.santander.genai.etl.service;

import com.santander.genai.etl.dto.ClienteDTO;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    ClienteDTO criar(ClienteDTO dto);
    Optional<ClienteDTO> buscar(Long id);
    List<ClienteDTO> listar();
    Optional<ClienteDTO> atualizar(Long id, ClienteDTO dto);
    void deletar(Long id);
}
