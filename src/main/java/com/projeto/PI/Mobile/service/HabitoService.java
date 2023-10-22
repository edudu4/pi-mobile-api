package com.projeto.PI.Mobile.service;

import com.projeto.PI.Mobile.controller.utils.ImageUtils;
import com.projeto.PI.Mobile.domain.Habito;
import com.projeto.PI.Mobile.repository.HabitoRepository;
import com.projeto.PI.Mobile.requests.HabitoPostRequestBody;
import com.projeto.PI.Mobile.requests.HabitoPutRequestBody;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitoService {
    private final HabitoRepository habitoRepository;
    private final UsuarioService usuarioService;

    public List<Habito> listAll() {
        return habitoRepository.findAll();
    }
    public Habito findById(long id){
        return habitoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hábito não encontrado."));
    }

    @Transactional(rollbackOn = Exception.class)
    public Habito save(HabitoPostRequestBody habitoPostRequestBody) {
        LocalDate dataFormatada = LocalDate.parse(habitoPostRequestBody.getDataInicio(),
                DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        LocalTime horaFormatada = LocalTime.parse(habitoPostRequestBody.getHorarioAlarme(),
                DateTimeFormatter.ofPattern("HH:mm"));


        Habito habito = Habito.builder()
                .nome(habitoPostRequestBody.getNome())
                .tocarAlarme(habitoPostRequestBody.isTocarAlarme())
                .usuario(usuarioService.findById(habitoPostRequestBody.getUsuarioId()))
                .dataInicio(dataFormatada)
                .horarioAlarme(horaFormatada)
                .usuario(usuarioService.findById(habitoPostRequestBody.getUsuarioId()))
                .imagem(ImageUtils.compressImage(habitoPostRequestBody.getImagem().getBytes()))
                .build();

        return habitoRepository.save(habito);
    }

    public void replace(HabitoPutRequestBody habitoPutRequestBody) {
        LocalDate dataFormatada = LocalDate.parse(habitoPutRequestBody.getDataInicio(),
                DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        LocalTime horaFormatada = LocalTime.parse(habitoPutRequestBody.getHorarioAlarme(),
                DateTimeFormatter.ofPattern("HH:mm"));

        Habito habitoSalvo = findById(habitoPutRequestBody.getId());

        if(habitoSalvo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hábito não encontrado");
        }
        habitoRepository.save(Habito.builder()
                .id(habitoSalvo.getId())
                .nome(habitoPutRequestBody.getNome())
                .imagem(habitoSalvo.getImagem())
                .horarioAlarme(horaFormatada)
                .dataInicio(dataFormatada)
                .tocarAlarme(habitoPutRequestBody.isTocarAlarme())
                .habitoConcluidos(habitoSalvo.getHabitoConcluidos())
                .usuario(habitoSalvo.getUsuario())
                .build());
    }
    public void uploadImage(MultipartFile imagem, long id) {
        try {
            Habito habito = findById(id);
            byte[] imagemBytes = imagem.getBytes();
            //habito.setImagem(imagemBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(long id ) {
        habitoRepository.delete(findById(id));
    }
}
