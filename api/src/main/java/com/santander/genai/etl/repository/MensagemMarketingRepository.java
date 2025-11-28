package com.santander.genai.etl.repository;

import com.santander.genai.etl.domain.MensagemMarketing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensagemMarketingRepository extends JpaRepository<MensagemMarketing, Long> {
    List<MensagemMarketing> findByClienteId(Long clienteId);
}
