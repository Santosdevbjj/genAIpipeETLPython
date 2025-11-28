package com.santander.genai.etl.mapper;

import com.santander.genai.etl.domain.Cliente;
import com.santander.genai.etl.domain.MensagemMarketing;
import com.santander.genai.etl.dto.ClienteDTO;
import com.santander.genai.etl.dto.MensagemDTO;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

    public ClienteDTO toDto(Cliente c) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(c.getId());
        dto.setNome(c.getNome());
        dto.setEmail(c.getEmail());
        dto.setSegmento(c.getSegmento());
        dto.setLimiteCredito(c.getLimiteCredito());
        dto.setScore(c.getScore());
        return dto;
    }

    public Cliente toEntity(ClienteDTO dto) {
        Cliente c = new Cliente();
        c.setId(dto.getId());
        c.setNome(dto.getNome());
        c.setEmail(dto.getEmail());
        c.setSegmento(dto.getSegmento());
        c.setLimiteCredito(dto.getLimiteCredito());
        c.setScore(dto.getScore());
        return c;
    }

    public MensagemDTO toDto(MensagemMarketing m) {
        MensagemDTO dto = new MensagemDTO();
        dto.setId(m.getId());
        dto.setClienteId(m.getClienteId());
        dto.setTexto(m.getTexto());
        dto.setCanal(m.getCanal());
        dto.setModeloVersao(m.getModeloVersao());
        return dto;
    }

    public MensagemMarketing toEntity(MensagemDTO dto) {
        MensagemMarketing m = new MensagemMarketing();
        m.setId(dto.getId());
        m.setClienteId(dto.getClienteId());
        m.setTexto(dto.getTexto());
        m.setCanal(dto.getCanal());
        m.setModeloVersao(dto.getModeloVersao());
        return m;
    }
}
