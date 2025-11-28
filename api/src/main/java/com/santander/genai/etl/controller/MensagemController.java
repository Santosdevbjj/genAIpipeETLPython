package com.santander.genai.etl.controller;

import com.santander.genai.etl.dto.MensagemDTO;
import com.santander.genai.etl.service.MensagemService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensagens")
public class MensagemController {

    private final MensagemService service;

    public MensagemController(MensagemService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('SCOPE_api:write')")
    @PostMapping
    public ResponseEntity<MensagemDTO> criar(@Valid @RequestBody MensagemDTO dto) {
        MensagemDTO created = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PreAuthorize("hasAuthority('SCOPE_api:read')")
    @GetMapping("/{id}")
    public ResponseEntity<MensagemDTO> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('SCOPE_api:read')")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<MensagemDTO>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.listarPorCliente(clienteId));
    }
}
