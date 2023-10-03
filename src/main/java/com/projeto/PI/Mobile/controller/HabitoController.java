package com.projeto.PI.Mobile.controller;

import com.projeto.PI.Mobile.domain.Habito;
import com.projeto.PI.Mobile.requests.HabitoPostRequestBody;
import com.projeto.PI.Mobile.requests.HabitoPutRequestBody;
import com.projeto.PI.Mobile.service.HabitoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<Habito>> list() {
        return ResponseEntity.ok(habitoService.listAll());
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<Habito> findById(@PathVariable long id) {
        return ResponseEntity.ok(habitoService.findById(id));
    }
    @PostMapping
    public ResponseEntity<Habito> save(@RequestBody HabitoPostRequestBody habitoPostRequestBody,
                                       @RequestPart MultipartFile imagem) {
        habitoPostRequestBody.setImagem(imagem);
        return new ResponseEntity<>(habitoService.save(habitoPostRequestBody),HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody HabitoPutRequestBody habitoPutRequestBody) {
        habitoService.replace(habitoPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping(path = "/upload")
    public ResponseEntity<Void> replaceImage(@RequestParam("file") MultipartFile imagem, @PathVariable long id) {
        habitoService.replaceImage(imagem, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id ) {
        habitoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
