package br.com.project.helpdesk.repository;


import br.com.project.helpdesk.domain.Chamado;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChamadoRepository extends MongoRepository<Chamado, String> {
}
