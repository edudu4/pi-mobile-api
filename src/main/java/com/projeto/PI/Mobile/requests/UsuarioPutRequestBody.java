package com.projeto.PI.Mobile.requests;

import lombok.Data;

@Data
public class UsuarioPutRequestBody {
    Long id;
    String senhaAtual;
    String novaSenha;
}
