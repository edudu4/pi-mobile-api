package com.projeto.PI.Mobile.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String email;
    String nome;
    String senha;
    @Column(name = "data_nascimento")
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate dataNascimento;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    List<Habito> habitos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    List<HabitoConcluido> habitoConcluidos;

}
