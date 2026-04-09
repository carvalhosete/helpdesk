package br.com.project.helpdesk.dto;

import br.com.project.helpdesk.domain.Chamado;
import br.com.project.helpdesk.domain.StatusChamado;

import java.time.LocalDateTime;

public record ChamadoResponseDTO(
        String id,
        String titulo,
        String descricao,
        StatusChamado status,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao,
        String idUsuarioCliente,
        String idUsuarioOperador
) {
    public static ChamadoResponseDTO fromEntity(Chamado chamado){
        return new ChamadoResponseDTO(
                chamado.getId(),
                chamado.getTitulo(),
                chamado.getDescricao(),
                chamado.getStatus(),
                chamado.getDataCriacao(),
                chamado.getDataAtualizacao(),
                chamado.getIdUsuarioCliente(),
                chamado.getIdUsuarioOperador()
        );
    }
}
