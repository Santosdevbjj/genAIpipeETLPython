package com.santander.genai.etl.service.impl;

import com.santander.genai.etl.domain.MensagemMarketing;
import com.santander.genai.etl.dto.MensagemDTO;
import com.santander.genai.etl.mapper.DtoMapper;
import com.santander.genai.etl.repository.MensagemMarketingRepository;
import com.santander.genai.etl.service.MensagemService;
import com.santander.genai.etl.util.IdempotencyUtil;
import com.santander.genai.etl.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MensagemServiceImpl implements MensagemService {

    private final MensagemMarketingRepository repository;
    private final DtoMapper mapper;

    public MensagemServiceImpl(MensagemMarketingRepository repository, DtoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public MensagemDTO criar(MensagemDTO dto) {
        ValidationUtil.validateMensagemDTO(dto);

        // Idempotência básica: evita duplicar o mesmo texto para o mesmo cliente/canal
        List<MensagemMarketing> existentes = repository.findByClienteId(dto.getClienteId());
        String fingerprint = IdempotencyUtil.fingerprint(dto.getClienteId(), dto.getTexto(), dto.getCanal());
        boolean duplicada = existentes.stream().anyMatch(m ->
                IdempotencyUtil.fingerprint(m.getClienteId(), m.getTexto(), m.getCanal()).equals(fingerprint));
        if (duplicada) {
            return mapper.toDto(existentes.stream()
                    .filter(m -> IdempotencyUtil.fingerprint(m.getClienteId(), m.getTexto(), m.getCanal()).equals(fingerprint))
                    .findFirst().get());
        }

        MensagemMarketing entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    public Optional<MensagemDTO> buscar(Long id) {
        return repository.findById(id).map(mapper::toDto);
    }

    @Override
    public List<MensagemDTO> listarPorCliente(Long clienteId) {
        return repository.findByClienteId(clienteId).stream().map(mapper::toDto).toList();
    }
}
