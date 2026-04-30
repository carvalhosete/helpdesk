package br.com.project.helpdesk.controller;

import br.com.project.helpdesk.dto.AtualizarStatusDTO;
import br.com.project.helpdesk.dto.ChamadoRequestDTO;
import br.com.project.helpdesk.dto.ChamadoResponseDTO;
import br.com.project.helpdesk.service.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    private final ChamadoService service;

    public ChamadoController(ChamadoService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ChamadoResponseDTO> criarChamado(@Valid @RequestBody ChamadoRequestDTO chamado, UriComponentsBuilder uriBuilder){
        ChamadoResponseDTO chamadoSalvo = service.criarChamado(chamado);
        URI uri = uriBuilder.path("/chamados/{id}").buildAndExpand(chamadoSalvo.id()).toUri();
        return ResponseEntity.created(uri).body(chamadoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<ChamadoResponseDTO>> listarChamados(){
        var chamados = service.listarChamados();
        return ResponseEntity.ok(chamados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChamadoResponseDTO> chamadosPorId(@PathVariable String id){
        var chamado = service.buscarChamadoPorId(id);
        return ResponseEntity.ok(chamado);
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<ChamadoResponseDTO> atualizarStatus(@PathVariable String id, @Valid @RequestBody AtualizarStatusDTO dto){
        ChamadoResponseDTO chamadoAtualizado = service.atualizarStatus(id, dto.status());

        return ResponseEntity.ok(chamadoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarChamado(@PathVariable String id){
        service.deletarChamado(id);

        return ResponseEntity.noContent().build();
    }
}
