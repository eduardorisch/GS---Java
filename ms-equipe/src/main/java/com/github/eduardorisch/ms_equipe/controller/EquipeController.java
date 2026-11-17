package com.github.eduardorisch.ms_equipe.controller;

import com.github.eduardorisch.ms_equipe.dtos.EquipeRequestDto;
import com.github.eduardorisch.ms_equipe.dtos.EquipeResponseDto;
import com.github.eduardorisch.ms_equipe.service.EquipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/equipes")
public class EquipeController {

    @Autowired
    private EquipeService service;

    @GetMapping
    public ResponseEntity<List<EquipeResponseDto>> getAll(){
        List<EquipeResponseDto> dto = service.findAllEquipes();

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipeResponseDto> getOne(@PathVariable Long id){
        EquipeResponseDto dto = service.findEquipeById(id);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<EquipeResponseDto> saveOcorrencia(@Valid @RequestBody EquipeRequestDto dto){
        EquipeResponseDto responseDto = service.saveEquipe(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(responseDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipeResponseDto> updateOcorrencia(@PathVariable Long id,
                                                                  @Valid @RequestBody EquipeRequestDto dto){
        EquipeResponseDto responseDto = service.updateEquipe(id, dto);

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOcorrencia(@PathVariable Long id){
        service.deleteEquipeById(id);

        return ResponseEntity.noContent().build();
    }
}
