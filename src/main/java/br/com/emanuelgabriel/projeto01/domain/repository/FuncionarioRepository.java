package br.com.emanuelgabriel.projeto01.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.emanuelgabriel.projeto01.domain.entity.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

	List<Funcionario> findByNome(String nome);
	
	@Query(value = "SELECT f FROM Funcionario f WHERE f.nome = :nome AND f.salario >= :salario AND f.dataContratacao = :dataContratacao")
	List<Funcionario> buscarPorNomeSalarioMaiorDataContratacao(String nome, Double salario, LocalDate dataContratacao);
	
}
