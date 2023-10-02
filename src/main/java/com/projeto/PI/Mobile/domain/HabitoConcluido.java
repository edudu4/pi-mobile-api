package com.projeto.PI.Mobile.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class HabitoConcluido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    boolean concluido;
    LocalDate dataConclusao;

    @ManyToOne
    @JoinColumn(name = "habito_id")
    Habito habito;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    Usuario usuario;
}
