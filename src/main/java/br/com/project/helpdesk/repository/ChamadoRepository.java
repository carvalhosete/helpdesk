package br.com.project.helpdesk.repository;


import br.com.project.helpdesk.domain.Chamado;
import br.com.project.helpdesk.domain.StatusChamado;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChamadoRepository extends MongoRepository<Chamado, String> {

    List<Chamado> findByStatusNot(StatusChamado status);

    Optional<Chamado> findByIdAndStatusNot(String id, StatusChamado status);
}
