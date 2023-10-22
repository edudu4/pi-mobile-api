package com.projeto.PI.Mobile.requests;

import lombok.Data;

@Data
public class UsuarioImagePostRequestBody {
    Long id;
    byte[] image;
}
