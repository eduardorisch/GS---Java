package com.github.eduardorisch.ms_equipe.dtos;

import com.github.eduardorisch.ms_equipe.entity.Equipe;
import com.github.eduardorisch.ms_equipe.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EquipeResponseDto {
    Long id;
    String nome;
    String especialidade;
    Long ocorrenciaId;
    Integer quantidadeIntegrantes;
    Status status;

    public EquipeResponseDto(Equipe equipe) {
        id = equipe.getId();
        nome = equipe.getNome();
        especialidade = equipe.getEspecialidade();
        ocorrenciaId = equipe.getOcorrenciaId();
        quantidadeIntegrantes = equipe.getQuantidadeIntegrantes();
        status = equipe.getStatus();
    }
}
