package com.projeto.PI.Mobile.requests;

import lombok.Data;

@Data
public class UsuarioPostRequestBody {
    String nome;
    String email;
    String dataNascimento;
    String senha;
}
