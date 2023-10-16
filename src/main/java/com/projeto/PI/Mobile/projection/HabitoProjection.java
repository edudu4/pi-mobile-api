package com.projeto.PI.Mobile.projection;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class HabitoProjection {
        private Long id;
        private String nome;
        private LocalDate dataInicio;
        private LocalTime horarioAlarme;
        private boolean tocarAlarme;
        private Long usuarioId;
        private String imagem;

}
