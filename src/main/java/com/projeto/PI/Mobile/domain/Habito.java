package com.projeto.PI.Mobile.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Habito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nome;
    @Column(name = "data_inicio")
    LocalDate dataInicio;
    @Column(name = "horario_alarme")
    LocalTime horarioAlarme;
    @Column(name = "tocar_alarme")
    boolean tocarAlarme;
    @Lob
    byte[] imagem;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    Usuario usuario;

    @OneToMany(mappedBy = "habito")
    List<HabitoConcluido> habitoConcluidos;
}
