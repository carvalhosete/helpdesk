package br.com.project.helpdesk.dto;

import br.com.project.helpdesk.domain.StatusChamado;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusDTO(
        @NotNull(message = "O status não pode ser nulo")
        StatusChamado status
) {}
