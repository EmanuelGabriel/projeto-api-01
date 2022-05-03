package br.com.emanuelgabriel.projeto01.domain.repository;

import java.time.LocalDate;
import java.util.List;

import br.com.emanuelgabriel.projeto01.domain.repository.funcionarios.FuncionarioRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.emanuelgabriel.projeto01.domain.entity.Funcionario;
import br.com.emanuelgabriel.projeto01.domain.repository.customers.FuncionarioProjecao;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>, FuncionarioRepositoryQuery, JpaSpecificationExecutor<Funcionario> {

	List<Funcionario> findByNome(String nome);

	Funcionario findByCpf(String cpf);

	@Query(value = "SELECT f FROM Funcionario f WHERE f.nome = :nome AND f.salario >= :salario AND f.dataContratacao = :dataContratacao")
	List<Funcionario> buscarPorNomeSalarioMaiorDataContratacao(String nome, Double salario, LocalDate dataContratacao);

	@Query(value = "SELECT f FROM Funcionario f JOIN FETCH f.cargo c WHERE c.descricao = :nomeCargo")
	List<Funcionario> buscarPorCargo(String nomeCargo);

	@Query(value = "SELECT * FROM tb_funcionario f WHERE f.data_contratacao >= :data ORDER BY f.data_contratacao ASC", nativeQuery = true)
	List<Funcionario> buscarPorDataContratacao(LocalDate data);
	
	List<Funcionario> findByDataContratacaoBetween(LocalDate dataInicio, LocalDate dataFinal);
	
	@Query(value = "SELECT f.id_funcionario, f.nome, f.cpf, f.salario FROM tb_funcionario f ORDER BY f.id_funcionario ASC", nativeQuery = true)
	List<FuncionarioProjecao> buscarFuncionarioMaiorSalario();

}
