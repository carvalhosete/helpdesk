package br.com.project.helpdesk.service;

import br.com.project.helpdesk.domain.Chamado;
import br.com.project.helpdesk.domain.StatusChamado;
import br.com.project.helpdesk.dto.ChamadoRequestDTO;
import br.com.project.helpdesk.dto.ChamadoResponseDTO;
import br.com.project.helpdesk.infra.ChamadoNaoEncontradoException;
import br.com.project.helpdesk.repository.ChamadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;

    public ChamadoResponseDTO criarChamado(ChamadoRequestDTO chamadoDTO){

        Chamado chamadoEntity = new Chamado();
        chamadoEntity.setTitulo(chamadoDTO.titulo());
        chamadoEntity.setDescricao(chamadoDTO.descricao());
        chamadoEntity.setStatus(StatusChamado.ABERTO);
        chamadoEntity.setIdUsuarioCliente(chamadoDTO.idUsuarioCliente());
        chamadoEntity.setIdUsuarioOperador("");

        Chamado chamadoSalvo = chamadoRepository.save(chamadoEntity);

        return ChamadoResponseDTO.fromEntity(chamadoSalvo);
    }

    public List<ChamadoResponseDTO> listarChamados(){
        return chamadoRepository.findByStatusNot(StatusChamado.CANCELADO)
                .stream()
                .map(ChamadoResponseDTO::fromEntity)
                .toList();
    }

    public ChamadoResponseDTO buscarChamadoPorId(String id){
        Chamado chamadoEncontrado = chamadoRepository.findByIdAndStatusNot(id, StatusChamado.CANCELADO)
                .orElseThrow(() -> new ChamadoNaoEncontradoException("Chamado não encontrado com o ID: " + id));

        return ChamadoResponseDTO.fromEntity(chamadoEncontrado);
    }

    public ChamadoResponseDTO atualizarStatus(String id, StatusChamado novoStatus){
        Chamado chamado = chamadoRepository.findByIdAndStatusNot(id, StatusChamado.CANCELADO)
                .orElseThrow(() -> new ChamadoNaoEncontradoException("Chamado não encontrado com o ID: " + id));

        chamado.setStatus(novoStatus);
        Chamado chamadoAtualizado = chamadoRepository.save(chamado);

        return ChamadoResponseDTO.fromEntity(chamadoAtualizado);
    }

    public void deletarChamado(String id){
        Chamado chamado = chamadoRepository.findByIdAndStatusNot(id, StatusChamado.CANCELADO)
                .orElseThrow(() -> new ChamadoNaoEncontradoException("Chamado não encontrado com o ID: " + id));
        chamado.setStatus(StatusChamado.CANCELADO);

        chamadoRepository.save(chamado);
    }
}
