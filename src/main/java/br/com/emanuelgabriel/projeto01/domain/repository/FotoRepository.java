package br.com.emanuelgabriel.projeto01.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.emanuelgabriel.projeto01.domain.entity.Foto;

/**
 * 
 * @author emanuel.sousa
 *
 */

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {

	Optional<Foto> findByNomeIgnoreCaseContaining(String nome);

	List<Foto> findByTypeEndingWithIgnoreCase(String type);

}
