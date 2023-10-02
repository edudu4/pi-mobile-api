package com.projeto.PI.Mobile.controller;

import com.projeto.PI.Mobile.domain.Usuario;
import com.projeto.PI.Mobile.requests.UsuarioPostRequestBody;
import com.projeto.PI.Mobile.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> list() {
        return ResponseEntity.ok(usuarioService.listAll());
    }
    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody UsuarioPostRequestBody usuarioPostRequestBody) {
        return new ResponseEntity<>(usuarioService.save(usuarioPostRequestBody), HttpStatus.CREATED);
    }
}