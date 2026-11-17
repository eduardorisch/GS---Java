package com.github.eduardorisch.ms_ocorrencia.service;

import com.github.eduardorisch.ms_ocorrencia.dtos.OcorrenciaRequestDto;
import com.github.eduardorisch.ms_ocorrencia.dtos.OcorrenciaResponseDto;
import com.github.eduardorisch.ms_ocorrencia.entities.Ocorrencia;
import com.github.eduardorisch.ms_ocorrencia.enums.Status;
import com.github.eduardorisch.ms_ocorrencia.exceptions.BusinessRuleException;
import com.github.eduardorisch.ms_ocorrencia.exceptions.ResourceNotFoundException;
import com.github.eduardorisch.ms_ocorrencia.repositories.OcorrenciaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OcorrenciaService {

    @Autowired
    private OcorrenciaRepository repository;

    @Transactional(readOnly = true)
    public List<OcorrenciaResponseDto> findAllOcorrencias() {

        return repository.findAll()
                .stream().map(OcorrenciaResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public OcorrenciaResponseDto findOcorrenciaById(Long id) {

        Ocorrencia ocorrencia = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + id)
        );

        return new OcorrenciaResponseDto(ocorrencia);
    }

    @Transactional
    public OcorrenciaResponseDto saveOcorrencia(OcorrenciaRequestDto dto) {

        Ocorrencia ocorrencia = new Ocorrencia();
        mapDtoToOcorrencia(dto, ocorrencia);

        ocorrencia.setStatus(Status.ABERTA);
        ocorrencia = repository.save(ocorrencia);
        return new OcorrenciaResponseDto(ocorrencia);
    }

    @Transactional
    public OcorrenciaResponseDto updateOcorrencia(Long id, OcorrenciaRequestDto dto) {
        try{
            Ocorrencia ocorrencia = repository.getReferenceById(id);
            mapDtoToOcorrencia(dto, ocorrencia);
            ocorrencia = repository.save(ocorrencia);
            return new OcorrenciaResponseDto(ocorrencia);
        } catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteOcorrenciaById(Long id) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }

        repository.deleteById(id);
    }

    @Transactional
    public void atribuirEquipe(Long ocorrenciaId, Long equipeId) {
        Ocorrencia ocorrencia = repository.findById(ocorrenciaId).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + ocorrenciaId));

        if (ocorrencia.getStatus() != Status.ABERTA) {
            throw new BusinessRuleException("Só é possível atribuir equipe a uma ocorrência aberta");
        }

        ocorrencia.setEquipeId(equipeId);
        ocorrencia.setStatus(Status.EM_ATENDIMENTO);
        repository.save(ocorrencia);
    }

    @Transactional
    public void finalizar(Long id) {
        Ocorrencia ocorrencia = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + id));

        if (ocorrencia.getStatus() == Status.RESOLVIDA) {
            return;
        }

        if (ocorrencia.getStatus() != Status.EM_ATENDIMENTO) {
            throw new BusinessRuleException("Só é possível finalizar uma ocorrência em atendimento");
        }

        ocorrencia.setStatus(Status.RESOLVIDA);
        repository.save(ocorrencia);
    }

    @Transactional
    public String retornarCategoriaOcorrencia(Long id){
        Ocorrencia ocorrencia = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + id)
        );

        return ocorrencia.getCategoria();
    }

    private void mapDtoToOcorrencia(OcorrenciaRequestDto dto, Ocorrencia ocorrencia) {
        ocorrencia.setTitulo(dto.getTitulo());
        ocorrencia.setDescricao(dto.getDescricao());
        ocorrencia.setCategoria(dto.getCategoria());
    }
}
