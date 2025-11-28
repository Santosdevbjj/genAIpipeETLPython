package com.santander.genai.etl.util;

import com.santander.genai.etl.dto.ClienteDTO;
import com.santander.genai.etl.dto.MensagemDTO;

public final class ValidationUtil {

    private ValidationUtil() {}

    public static void validateClienteDTO(ClienteDTO dto) {
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (dto.getSegmento() == null || dto.getSegmento().isBlank()) {
            throw new IllegalArgumentException("Segmento é obrigatório");
        }
    }

    public static void validateMensagemDTO(MensagemDTO dto) {
        if (dto.getClienteId() == null) {
            throw new IllegalArgumentException("ClienteId é obrigatório");
        }
        if (dto.getTexto() == null || dto.getTexto().isBlank()) {
            throw new IllegalArgumentException("Texto é obrigatório");
        }
        if (dto.getCanal() == null || dto.getCanal().isBlank()) {
            throw new IllegalArgumentException("Canal é obrigatório");
        }
    }
}
