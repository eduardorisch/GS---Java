package com.github.eduardorisch.ms_equipe.dtos;

import com.github.eduardorisch.ms_equipe.entity.Equipe;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EquipeRequestDto{

    @NotBlank(message = "O campo é obrigatório")
    @Size(min = 3, max = 50, message = "O campo deve ter no mínimo 3 e no máximo 50 caracteres")
    String nome;

    @NotBlank(message = "O campo é obrigatório")
    @Size(min = 3, max = 100, message = "O campo deve ter no mínimo 3 e no máximo 100 caracteres")
    String especialidade;

    @NotNull(message = "O campo é obrigatório")
    @Min(value = 1, message = "O campo deve ser maior que zero")
    Integer quantidadeIntegrantes;

    public EquipeRequestDto(Equipe equipe) {
        nome = equipe.getNome();
        especialidade = equipe.getEspecialidade();
        quantidadeIntegrantes = equipe.getQuantidadeIntegrantes();
    }
}
