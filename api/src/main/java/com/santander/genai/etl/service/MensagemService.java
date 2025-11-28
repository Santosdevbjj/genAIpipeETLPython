package com.santander.genai.etl.service;

import com.santander.genai.etl.dto.MensagemDTO;

import java.util.List;
import java.util.Optional;

public interface MensagemService {
    MensagemDTO criar(MensagemDTO dto);
    Optional<MensagemDTO> buscar(Long id);
    List<MensagemDTO> listarPorCliente(Long clienteId);
}
