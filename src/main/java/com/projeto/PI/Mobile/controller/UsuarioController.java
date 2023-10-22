package com.projeto.PI.Mobile.controller;

import com.projeto.PI.Mobile.domain.Usuario;
import com.projeto.PI.Mobile.requests.UsuarioAuthRequestBody;
import com.projeto.PI.Mobile.requests.UsuarioImagePostRequestBody;
import com.projeto.PI.Mobile.requests.UsuarioPostRequestBody;
import com.projeto.PI.Mobile.requests.UsuarioPutRequestBody;
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

    @PostMapping(path = "/auth")
    public ResponseEntity<Usuario> auth(@RequestBody UsuarioAuthRequestBody usuarioAuthRequestBody) {
        return ResponseEntity.ok(usuarioService.auth(usuarioAuthRequestBody));
    }

    @PostMapping(path = "/uploadProfileImage")
    public ResponseEntity<Void> uploadProfileImage(@RequestBody UsuarioImagePostRequestBody usuarioImagePostRequestBody) {
        usuarioService.uploadImageProfile(usuarioImagePostRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody UsuarioPutRequestBody usuarioPutRequestBody) {
        usuarioService.replace(usuarioPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        usuarioService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
