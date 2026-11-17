package com.github.eduardorisch.ms_equipe.controller;

import com.github.eduardorisch.ms_equipe.dtos.EquipeRequestDto;
import com.github.eduardorisch.ms_equipe.dtos.EquipeResponseDto;
import com.github.eduardorisch.ms_equipe.service.EquipeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
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
    public ResponseEntity<EquipeResponseDto> saveEquipe(@Valid @RequestBody EquipeRequestDto dto){
        EquipeResponseDto responseDto = service.saveEquipe(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(responseDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipeResponseDto> updateEquipe(@PathVariable Long id,
                                                                  @Valid @RequestBody EquipeRequestDto dto){
        EquipeResponseDto responseDto = service.updateEquipe(id, dto);

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipe(@PathVariable Long id){
        service.deleteEquipeById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{equipeId}/assumir/{ocorrenciaId}")
    @CircuitBreaker(name = "assumirOcorrencia",
                    fallbackMethod = "fallbackAssumirOcorrencia")
    public ResponseEntity<EquipeResponseDto> assumirOcorrencia (@PathVariable Long equipeId,
                                                @PathVariable Long ocorrenciaId){
        EquipeResponseDto responseDto = service.assumirOcorrencia(equipeId, ocorrenciaId);

        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/finalizar/{id}")
    @CircuitBreaker(name = "finalizar",
            fallbackMethod = "fallbackFinalizar")
    public ResponseEntity<EquipeResponseDto> finalizarOcorrencia(@PathVariable Long id){
        EquipeResponseDto responseDto = service.finalizar(id);

        return ResponseEntity.ok(responseDto);
    }

    public ResponseEntity<EquipeResponseDto>fallbackAssumirOcorrencia(Long equipeId, Long ocorrenciaId, Throwable e){
        log.error("Falha ao Equipe {} assumir Ocorrencia {}. Ativando FallBack. Erro {}", equipeId, ocorrenciaId, e.getMessage());
        EquipeResponseDto dto = service.findEquipeById(equipeId);

        return ResponseEntity.status(503).body(dto);
    }

    public ResponseEntity<EquipeResponseDto>fallbackFinalizar(Long id, Throwable e){
        log.error("Falha na Equipe {} finalizando. Ativando FallBack. Erro {}", id, e.getMessage());
        EquipeResponseDto dto = service.findEquipeById(id);

        return ResponseEntity.status(503).body(dto);
    }
}
