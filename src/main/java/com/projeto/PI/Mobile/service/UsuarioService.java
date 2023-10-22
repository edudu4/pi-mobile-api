package com.projeto.PI.Mobile.service;

import com.projeto.PI.Mobile.controller.utils.ImageUtils;
import com.projeto.PI.Mobile.domain.Usuario;
import com.projeto.PI.Mobile.repository.UsuarioRepository;
import com.projeto.PI.Mobile.requests.UsuarioAuthRequestBody;
import com.projeto.PI.Mobile.requests.UsuarioImagePostRequestBody;
import com.projeto.PI.Mobile.requests.UsuarioPostRequestBody;
import com.projeto.PI.Mobile.requests.UsuarioPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public List<Usuario> listAll() {
        return usuarioRepository.findAll();
    }

    public Usuario auth(UsuarioAuthRequestBody usuarioAuthRequestBody) {
        Usuario usuario = findByEmail(usuarioAuthRequestBody.getEmail());
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou Senha incorreto");
        }
        if (!usuarioAuthRequestBody.getSenha().equals(usuario.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou Senha incorreto");
        }

        if(usuario.getFotoPerfil() != null) {
            usuario.setFotoPerfil(ImageUtils.decompressImage(usuario.getFotoPerfil()));
        }
        return usuario;
    }

    public Usuario findById(long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));
    }

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario save(UsuarioPostRequestBody usuarioPostRequestBody) {
        Usuario usuarioJaExistente = findByEmail(usuarioPostRequestBody.getEmail());
        if (usuarioJaExistente == null) {
            LocalDate dataFormatada = LocalDate.parse(usuarioPostRequestBody.getDataNascimento(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return usuarioRepository.save(Usuario.builder()
                    .nome(usuarioPostRequestBody.getNome())
                    .dataNascimento(dataFormatada)
                    .email(usuarioPostRequestBody.getEmail())
                    .senha(usuarioPostRequestBody.getSenha())
                    .fotoPerfil(null)
                    .build());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado");
        }
    }

    public void uploadImageProfile(UsuarioImagePostRequestBody usuarioImagePostRequestBody) {
        Usuario usuarioJaExistente = findById(usuarioImagePostRequestBody.getId());
        if (usuarioJaExistente != null) {
                usuarioJaExistente.setFotoPerfil(ImageUtils.compressImage(usuarioImagePostRequestBody.getImage()));
                usuarioRepository.save(usuarioJaExistente);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado");
        }
    }

    public void replace(UsuarioPutRequestBody usuarioPutRequestBody) {
        Usuario usuarioSalvo = findById(usuarioPutRequestBody.getId());
        if (usuarioSalvo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado");
        }
        if (usuarioSalvo.getSenha().equals(usuarioPutRequestBody.getSenhaAtual())) {
            usuarioSalvo.setSenha(usuarioPutRequestBody.getNovaSenha());
            usuarioRepository.save(usuarioSalvo);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha incorreta");
        }
    }

    public void delete(long id) {
        usuarioRepository.delete(findById(id));
    }
}
