package com.santander.genai.etl.service.impl;

import com.santander.genai.etl.domain.Cliente;
import com.santander.genai.etl.dto.ClienteDTO;
import com.santander.genai.etl.mapper.DtoMapper;
import com.santander.genai.etl.repository.ClienteRepository;
import com.santander.genai.etl.service.ClienteService;
import com.santander.genai.etl.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;
    private final DtoMapper mapper;

    public ClienteServiceImpl(ClienteRepository repository, DtoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ClienteDTO criar(ClienteDTO dto) {
        ValidationUtil.validateClienteDTO(dto);
        Cliente entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    public Optional<ClienteDTO> buscar(Long id) {
        return repository.findById(id).map(mapper::toDto);
    }

    @Override
    public List<ClienteDTO> listar() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public Optional<ClienteDTO> atualizar(Long id, ClienteDTO dto) {
        return repository.findById(id).map(existing -> {
            existing.setNome(dto.getNome());
            existing.setEmail(dto.getEmail());
            existing.setSegmento(dto.getSegmento());
            existing.setLimiteCredito(dto.getLimiteCredito());
            existing.setScore(dto.getScore());
            return mapper.toDto(repository.save(existing));
        });
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
