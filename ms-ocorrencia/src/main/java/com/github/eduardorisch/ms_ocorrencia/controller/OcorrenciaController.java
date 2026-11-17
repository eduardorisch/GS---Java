package com.github.eduardorisch.ms_ocorrencia.controller;

import com.github.eduardorisch.ms_ocorrencia.dtos.OcorrenciaRequestDto;
import com.github.eduardorisch.ms_ocorrencia.dtos.OcorrenciaResponseDto;
import com.github.eduardorisch.ms_ocorrencia.service.OcorrenciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/ocorrencias")
public class OcorrenciaController {

    @Autowired
    private OcorrenciaService service;

    @GetMapping
    public ResponseEntity<List<OcorrenciaResponseDto>> getAll(){
        List<OcorrenciaResponseDto> dto = service.findAllOcorrencias();

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OcorrenciaResponseDto> getOne(@PathVariable Long id){
        OcorrenciaResponseDto dto = service.findOcorrenciaById(id);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<OcorrenciaResponseDto> saveOcorrencia(@Valid @RequestBody OcorrenciaRequestDto dto){
       OcorrenciaResponseDto responseDto = service.saveOcorrencia(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(responseDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OcorrenciaResponseDto> updateOcorrencia(@PathVariable Long id,
                                                                  @Valid @RequestBody OcorrenciaRequestDto dto){
        OcorrenciaResponseDto responseDto = service.updateOcorrencia(id, dto);

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOcorrencia(@PathVariable Long id){
        service.deleteOcorrenciaById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{ocorrenciaId}/atribuir/{equipeId}")
    public ResponseEntity<Void> atribuirEquipe (@PathVariable Long ocorrenciaId,
                                                @PathVariable Long equipeId){
        service.atribuirEquipe(ocorrenciaId, equipeId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Void> finalizarOcorrencia(@PathVariable Long id){
        service.finalizar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/categoria")
    public ResponseEntity<String> retornarCategoria(@PathVariable Long id){
        return ResponseEntity.ok(service.retornarCategoriaOcorrencia(id));
    }
}
