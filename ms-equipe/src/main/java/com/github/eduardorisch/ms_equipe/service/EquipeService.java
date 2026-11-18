package com.github.eduardorisch.ms_equipe.service;

import com.github.eduardorisch.ms_equipe.client.OcorrenciaClient;
import com.github.eduardorisch.ms_equipe.dtos.EquipeRequestDto;
import com.github.eduardorisch.ms_equipe.dtos.EquipeResponseDto;
import com.github.eduardorisch.ms_equipe.entity.Equipe;
import com.github.eduardorisch.ms_equipe.enums.Status;
import com.github.eduardorisch.ms_equipe.exceptions.BusinessRuleException;
import com.github.eduardorisch.ms_equipe.exceptions.ResourceNotFoundException;
import com.github.eduardorisch.ms_equipe.repositories.EquipeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EquipeService {

    @Autowired
    private EquipeRepository repository;

    @Autowired
    private OcorrenciaClient client;

    @Transactional(readOnly = true)
    public List<EquipeResponseDto> findAllEquipes() {

        return repository.findAll()
                .stream().map(EquipeResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public EquipeResponseDto findEquipeById(Long id) {

        Equipe equipe = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recursos não encontrados. ID: " +id)
        );

        return new EquipeResponseDto(equipe);
    }

    @Transactional
    public EquipeResponseDto saveEquipe(EquipeRequestDto dto) {

        Equipe equipe = new Equipe();
        mapDtoToEquipe(dto, equipe);

        equipe.setStatus(Status.DISPONIVEL);

        equipe = repository.save(equipe);
        return new EquipeResponseDto(equipe);
    }

    @Transactional
    public EquipeResponseDto updateEquipe(Long id, EquipeRequestDto dto) {

        try{
            Equipe equipe = repository.getReferenceById(id);
            mapDtoToEquipe(dto, equipe);
            equipe = repository.save(equipe);
            return new EquipeResponseDto(equipe);
        } catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteEquipeById(Long id) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }

        repository.deleteById(id);
    }

    public EquipeResponseDto assumirOcorrencia(Long equipeId, Long ocorrenciaId) {
        Equipe equipe = repository.findById(equipeId).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + equipeId)
        );

        String categoriaOcorrencia = client.retornarCategoria(ocorrenciaId);

        if (equipe.getStatus() != Status.DISPONIVEL) {
            throw new BusinessRuleException("A equipe não está disponível");
        }

        if (!equipe.getEspecialidade().equalsIgnoreCase(categoriaOcorrencia)) {
            throw new BusinessRuleException("A equipe não atende a categoria da ocorrência");
        }

        client.atribuirEquipe(ocorrenciaId, equipeId);

        equipe.setOcorrenciaId(ocorrenciaId);
        equipe.setStatus(Status.EM_ATENDIMENTO);
        equipe = repository.save(equipe);
        return new EquipeResponseDto(equipe);
    }

    public EquipeResponseDto finalizar(Long id) {
        Equipe equipe = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + id)
        );

        if (equipe.getStatus() != Status.EM_ATENDIMENTO || equipe.getOcorrenciaId() == null) {
            throw new BusinessRuleException("A equipe não está atendendo nenhuma ocorrência");
        }

        client.finalizar(equipe.getOcorrenciaId());

        equipe.setOcorrenciaId(null);
        equipe.setStatus(Status.DISPONIVEL);
        equipe = repository.save(equipe);
        return new EquipeResponseDto(equipe);
    }

    private void mapDtoToEquipe(EquipeRequestDto dto, Equipe equipe) {

        equipe.setNome(dto.getNome());
        equipe.setEspecialidade(dto.getEspecialidade());
        equipe.setQuantidadeIntegrantes(dto.getQuantidadeIntegrantes());
    }
}
