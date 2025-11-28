package com.santander.genai.etl.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 120)
    private String nome;

    @Email
    @NotBlank
    @Size(max = 160)
    private String email;

    @NotBlank
    @Size(max = 40)
    private String segmento; // Varejo, Select, Private

    @Min(0)
    @Max(1000000)
    private Integer limiteCredito;

    @Min(0)
    @Max(1000)
    private Integer score;

    // getters and setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSegmento() { return segmento; }
    public void setSegmento(String segmento) { this.segmento = segmento; }

    public Integer getLimiteCredito() { return limiteCredito; }
    public void setLimiteCredito(Integer limiteCredito) { this.limiteCredito = limiteCredito; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
}
