package br.com.project.helpdesk.dto;

import jakarta.validation.constraints.NotBlank;

public record ChamadoRequestDTO(

        @NotBlank(message = "O TÍTULO do chamado é de preenchimento obrigatório.")
        String titulo,

        @NotBlank(message = "A DESCRIÇÃO do chamado é de preenchimento obrigatório.")
        String descricao,

        @NotBlank(message = "O ID do user:CLIENTE é OBRIGATÓRIO")
        String idUsuarioCliente
) {}
