package com.github.eduardorisch.ms_ocorrencia.dtos;

import com.github.eduardorisch.ms_ocorrencia.entities.Ocorrencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OcorrenciaRequestDto{

    @NotBlank(message = "O campo é obrigatória")
    @Size(min = 3, max = 50, message = "O campo deve ter no minimo 3 e no máximo 50 caracteres")
    String titulo;

    @NotBlank(message = "O campo é obrigatória")
    @Size(min = 3, max = 100, message = "O campo deve ter no minimo 3 e no máximo 100 caracteres")
    String descricao;

    @NotBlank(message = "O campo é obrigatória")
    @Size(min = 3, max = 50, message = "O campo deve ter no minimo 3 e no máximo 50 caracteres")
    String categoria;

    public OcorrenciaRequestDto(Ocorrencia ocorrencia) {
        titulo = ocorrencia.getTitulo();
        descricao = ocorrencia.getDescricao();
        categoria = ocorrencia.getCategoria();
    }
}