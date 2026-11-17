package com.github.eduardorisch.ms_ocorrencia.repositories;

import com.github.eduardorisch.ms_ocorrencia.entities.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {}
