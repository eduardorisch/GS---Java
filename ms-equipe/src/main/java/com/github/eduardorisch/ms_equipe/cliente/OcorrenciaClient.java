package com.github.eduardorisch.ms_equipe.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("ms-ocorrencia")
public interface OcorrenciaClient{
    @RequestMapping(method = RequestMethod.PUT,
            value = ("/api/v1/ocorrencias/{ocorrenciaId}/atribuir/{equipeId}"))
    void atribuirEquipe(@PathVariable Long ocorrenciaId,
                        @PathVariable Long equipeId);

    @RequestMapping(method = RequestMethod.PUT,
            value = ("/api/v1/ocorrencias/{id}/finalizar"))
    void finalizar(@PathVariable Long id);

    @GetMapping("/api/v1/ocorrencias/{id}/categoria")
    String retornarCategoria (@PathVariable Long id);
}
