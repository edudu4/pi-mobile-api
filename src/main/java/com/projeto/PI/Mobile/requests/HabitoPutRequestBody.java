package com.projeto.PI.Mobile.requests;

import lombok.Data;

@Data
public class HabitoPutRequestBody {
    long id;
    String nome;
    String dataInicio;
    String horarioAlarme;
    boolean tocarAlarme;

}
