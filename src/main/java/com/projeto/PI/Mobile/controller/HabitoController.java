package com.projeto.PI.Mobile.controller;

import com.projeto.PI.Mobile.domain.Habito;
import com.projeto.PI.Mobile.projection.HabitoProjection;
import com.projeto.PI.Mobile.requests.HabitoPostRequestBody;
import com.projeto.PI.Mobile.requests.HabitoPutRequestBody;
import com.projeto.PI.Mobile.service.HabitoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("habitos")
@RestController
@AllArgsConstructor
public class HabitoController {
    private final HabitoService habitoService;

    @GetMapping
    public ResponseEntity<List<HabitoProjection>> list() {
        List<Habito> habitos = habitoService.listAll();
        List<HabitoProjection> habitoProjections = habitos.stream()
                .map(habito -> {
                    HabitoProjection habitoProjection = new HabitoProjection();
                    habitoProjection.setId(habito.getId());
                    habitoProjection.setNome(habito.getNome());
                    habitoProjection.setDataInicio(habito.getDataInicio());
                    habitoProjection.setHorarioAlarme(habito.getHorarioAlarme());
                    habitoProjection.setTocarAlarme(habito.isTocarAlarme());
                    habitoProjection.setUsuarioId(habito.getUsuario().getId());
                    habitoProjection.setImagem(habito.getImagem());
                    return habitoProjection;
                }).toList();
        return new ResponseEntity<>(habitoProjections, HttpStatus.OK);
    }

    @GetMapping(path = "/image/{id}")
    public ResponseEntity<?> habitoImagem(@PathVariable long id) {
        Habito habito = habitoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/jpg"))
                .body(habito.getImagem());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Habito> findById(@PathVariable long id) {
        return ResponseEntity.ok(habitoService.findById(id));
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Habito> save(@RequestBody HabitoPostRequestBody habitoPostRequestBody) {
        return new ResponseEntity<>(habitoService.save(habitoPostRequestBody), HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody HabitoPutRequestBody habitoPutRequestBody) {
        habitoService.replace(habitoPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/{id}/upload")
    public ResponseEntity<Void> uploadImage(@RequestParam("file") MultipartFile imagem, @PathVariable long id) {
        habitoService.uploadImage(imagem, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        habitoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
