package br.com.project.helpdesk.dto;

import br.com.project.helpdesk.domain.StatusChamado;

import java.time.LocalDateTime;

public record ChamadoResponseDTO(
        String id,
        String titulo,
        String descricao,
        StatusChamado status,
        LocalDateTime dataCriacao
) {}
