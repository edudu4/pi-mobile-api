package com.projeto.PI.Mobile.service;

import com.projeto.PI.Mobile.domain.Usuario;
import com.projeto.PI.Mobile.repository.UsuarioRepository;
import com.projeto.PI.Mobile.requests.UsuarioPostRequestBody;
import com.projeto.PI.Mobile.requests.UsuarioPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public Usuario findById(long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));
    }
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    public Usuario save(UsuarioPostRequestBody usuarioPostRequestBody) {
        Usuario usuarioJaExistente = findByEmail(usuarioPostRequestBody.getEmail());
        if(usuarioJaExistente == null) {
            LocalDate dataFormatada = LocalDate.parse(usuarioPostRequestBody.getDataNascimento(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return usuarioRepository.save(Usuario.builder()
                    .nome(usuarioPostRequestBody.getNome())
                    .dataNascimento(dataFormatada)
                    .email(usuarioPostRequestBody.getEmail())
                    .senha(usuarioPostRequestBody.getSenha())
                    .build());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado");
        }
    }
    public void replace(UsuarioPutRequestBody usuarioPutRequestBody) {
        Usuario usuarioSalvo = findById(usuarioPutRequestBody.getId());
        if(usuarioSalvo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado");
        }
        if(usuarioSalvo.getSenha().equals(usuarioPutRequestBody.getSenhaAtual())) {
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
