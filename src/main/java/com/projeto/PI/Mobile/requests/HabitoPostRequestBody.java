package com.projeto.PI.Mobile.requests;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Data
public class HabitoPostRequestBody {
    long usuarioId;
    String nome;
    String dataInicio;
    String horarioAlarme;
    boolean tocarAlarme;
    String imagem;

}
