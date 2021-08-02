package br.com.emanuelgabriel.projeto01.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.emanuelgabriel.projeto01.domain.entity.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

	Cargo findByDescricao(String descricao);
	
}
