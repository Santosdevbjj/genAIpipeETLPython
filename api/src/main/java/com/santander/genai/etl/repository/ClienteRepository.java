package com.santander.genai.etl.repository;

import com.santander.genai.etl.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
