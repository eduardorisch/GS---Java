package com.github.eduardorisch.ms_ocorrencia.entities;

import com.github.eduardorisch.ms_ocorrencia.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_ocorrencia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private String categoria;
    private Long equipeId;
    private Status status;

}
