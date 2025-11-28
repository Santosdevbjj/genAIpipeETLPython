package com.santander.genai.etl.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;

@Entity
@Table(name = "mensagens_marketing")
public class MensagemMarketing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="cliente_id", nullable=false)
    private Long clienteId;

    @NotBlank
    @Size(max = 1000)
    @Column(columnDefinition = "TEXT")
    private String texto;

    @NotBlank
    @Size(max = 20)
    private String canal; // email, sms, app

    @Column(name="modelo_versao", length=50)
    private String modeloVersao;

    @Column(name="created_at", nullable=false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

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

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
