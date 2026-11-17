package com.github.eduardorisch.ms_ocorrencia.dtos;

import com.github.eduardorisch.ms_ocorrencia.entities.Ocorrencia;
import com.github.eduardorisch.ms_ocorrencia.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OcorrenciaResponseDto {
    Long id;
    String titulo;
    String descricao;
    String categoria;
    Long equipeId;
    Status status;
    public OcorrenciaResponseDto(Ocorrencia ocorrencia) {
        id = ocorrencia.getId();
        titulo = ocorrencia.getTitulo();
        descricao = ocorrencia.getDescricao();
        categoria = ocorrencia.getCategoria();
        equipeId = ocorrencia.getEquipeId();
        status = ocorrencia.getStatus();
    }
}
