package com.projeto.PI.Mobile.domain;

import com.fasterxml.jackson.annotation.*;
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
    @JsonIgnore
    String imagem;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    Usuario usuario;

    @OneToMany(mappedBy = "habito")
    @JsonIgnore
    List<HabitoConcluido> habitoConcluidos;
}
