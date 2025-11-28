package com.santander.genai.etl.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MensagemDTO {

    private Long id;

    @NotNull
    private Long clienteId;

    @NotBlank
    @Size(max = 1000)
    private String texto;

    @NotBlank
    @Size(max = 20)
    private String canal;

    private String modeloVersao;

    // getters and setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public String getCanal() { return canal; }
    public void setCanal(String canal) { this.canal = canal; }

    public String getModeloVersao() { return modeloVersao; }
    public void setModeloVersao(String modeloVersao) { this.modeloVersao = modeloVersao; }
}
