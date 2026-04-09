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
        chamadoEntity.setDataCriacao(LocalDateTime.now());

        Chamado chamadoSalvo = chamadoRepository.save(chamadoEntity);

        return new ChamadoResponseDTO(
                chamadoSalvo.getId(),
                chamadoSalvo.getTitulo(),
                chamadoSalvo.getDescricao(),
                chamadoSalvo.getStatus(),
                chamadoSalvo.getDataCriacao()
        );
    }

    public List<ChamadoResponseDTO> listarChamados(){
        return chamadoRepository.findAll()
                .stream()
                .map(c -> new ChamadoResponseDTO(c.getId(),c.getTitulo(),c.getDescricao(),c.getStatus(),c.getDataCriacao()))
                .toList();
    }

    public ChamadoResponseDTO buscarChamadoPorId(String id){
        Chamado chamadoEncontrado = chamadoRepository.findById(id)
                .orElseThrow(() -> new ChamadoNaoEncontradoException("Chamado não encontrado com o ID: " + id));
        return new ChamadoResponseDTO(
                chamadoEncontrado.getId(),
                chamadoEncontrado.getTitulo(),
                chamadoEncontrado.getDescricao(),
                chamadoEncontrado.getStatus(),
                chamadoEncontrado.getDataCriacao()
        );
    }

    public ChamadoResponseDTO atualizarStatus(String id, StatusChamado novoStatus){
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new ChamadoNaoEncontradoException("Chamado não encontrado com o ID: " + id));

        chamado.setStatus(novoStatus);
        Chamado chamadoAtualizado = chamadoRepository.save(chamado);

        return new ChamadoResponseDTO(
                chamadoAtualizado.getId(),
                chamadoAtualizado.getTitulo(),
                chamadoAtualizado.getDescricao(),
                chamadoAtualizado.getStatus(),
                chamadoAtualizado.getDataCriacao()
        );
    }

    public void deletarChamado(String id){
        Chamado chamado = chamadoRepository.findById(id)
                .orElseThrow(() -> new ChamadoNaoEncontradoException("Chamado não encontrado com o ID: " + id));

        chamadoRepository.delete(chamado);
    }
}
