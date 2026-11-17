package com.github.eduardorisch.ms_equipe.entity;

import com.github.eduardorisch.ms_equipe.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_equipe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String especialidade;
    private Long ocorrenciaId;
    private Integer quantidadeIntegrantes;
    private Status status;
}
