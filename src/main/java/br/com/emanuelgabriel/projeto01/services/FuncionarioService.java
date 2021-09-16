package br.com.emanuelgabriel.projeto01.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.emanuelgabriel.projeto01.domain.dto.request.FuncionarioModelRequest;
import br.com.emanuelgabriel.projeto01.domain.dto.response.FuncionarioModelResponse;
import br.com.emanuelgabriel.projeto01.domain.entity.Cargo;
import br.com.emanuelgabriel.projeto01.domain.entity.Funcionario;
import br.com.emanuelgabriel.projeto01.domain.mapper.FuncionarioMapper;
import br.com.emanuelgabriel.projeto01.domain.repository.CargoRepository;
import br.com.emanuelgabriel.projeto01.domain.repository.FuncionarioRepository;
import br.com.emanuelgabriel.projeto01.domain.repository.customers.FuncionarioProjecao;
import br.com.emanuelgabriel.projeto01.domain.repository.specification.FuncionarioSpecification;
import br.com.emanuelgabriel.projeto01.services.exception.ObjNaoEncontradoException;
import br.com.emanuelgabriel.projeto01.services.exception.RegraNegocioException;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author emanuel.sousa
 *
 */

@Slf4j
@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioMapper funcionarioMapper;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private CargoRepository cargoRepository;

	public FuncionarioModelResponse buscarPorId(Long id) {
		log.info("Busca funcionário por ID {}", id);
		Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(id);
		if (!funcionarioOpt.isPresent()) {
			throw new ObjNaoEncontradoException("Funcionário de ID não encontrado");
		}
		return this.funcionarioMapper.entityToDTO(funcionarioOpt.get());

	}

	public FuncionarioModelResponse buscarPorCPF(String cpf) {
		log.info("Busca funcionário por CPF {}", cpf);
		Funcionario funcionario = funcionarioRepository.findByCpf(cpf);
		if (funcionario == null) {
			throw new ObjNaoEncontradoException("Funcionário de CPF não encontrado");
		}
		return this.funcionarioMapper.entityToDTO(funcionario);

	}

	public FuncionarioModelResponse salvar(FuncionarioModelRequest request) {
		log.info("Cria um funcionário {}", request);
		Funcionario funcionarioExistente = funcionarioRepository.findByCpf(request.getCpf());
		if (funcionarioExistente != null && !funcionarioExistente.equals(request)) {
			throw new RegraNegocioException("Já existe funcionário registrado com este CPF");
		}

		Optional<Cargo> cargoOpt = cargoRepository.findById(request.getCargo().getId());

		Funcionario funcionario = funcionarioMapper.dtoToEntity(request);
		funcionario.setCargo(cargoOpt.get());

		return funcionarioMapper.entityToDTO(funcionarioRepository.save(funcionario));
	}

	public Page<FuncionarioModelResponse> buscarTodos() {
		log.info("Busca todos os cargos");
		Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending().and(Sort.by("nome").ascending()));
		Page<Funcionario> cargos = funcionarioRepository.findAll(pageable);
		return funcionarioMapper.mapEntityPageToDTO(pageable, cargos);
	}

	public List<FuncionarioModelResponse> buscarPorCargo(String nomeCargo) {
		log.info("Busca funcionário por seu cargo {}", nomeCargo);
		List<Funcionario> porCargo = funcionarioRepository.buscarPorCargo(nomeCargo);
		if (porCargo.isEmpty()) {
			throw new ObjNaoEncontradoException("Não há funcionários para este cargo");
		}
		return funcionarioMapper.listEntityToDTO(porCargo);
	}

	public List<FuncionarioModelResponse> buscarPorMaiorSalarioDataContratacao(String nome, Double salario,
			LocalDate dataContratacao) {
		log.info("Busca funcionário por {} - {} - {}", nome, salario, dataContratacao);
		List<Funcionario> buscarPor = funcionarioRepository.buscarPorNomeSalarioMaiorDataContratacao(nome, salario,
				dataContratacao);
		if (buscarPor.isEmpty()) {
			throw new ObjNaoEncontradoException("Não há funcionários para esta busca");
		}
		return funcionarioMapper.listEntityToDTO(buscarPor);
	}

	public List<FuncionarioModelResponse> buscarPorDataContratacao(LocalDate dataContratacao) {
		log.info("Busca funcionário por sua data de contratação {}", dataContratacao);
		List<Funcionario> funcionarios = funcionarioRepository.buscarPorDataContratacao(dataContratacao);
		if (funcionarios.isEmpty()) {
			throw new ObjNaoEncontradoException("Não foi encontrado funcionário nesta data de contratação");
		}

		return funcionarioMapper.listEntityToDTO(funcionarios);
	}

	public List<FuncionarioModelResponse> buscarPorPeriodoDataContratacao(LocalDate dataInicio, LocalDate dataFinal) {
		log.info("Busca funcionário por período de data de contratação inicial {} e final {}", dataInicio, dataFinal);
		List<Funcionario> funcionarios = funcionarioRepository.findByDataContratacaoBetween(dataInicio, dataFinal);
		if (funcionarios.isEmpty()) {
			throw new ObjNaoEncontradoException("Não foi encontrado funcionários contratados neste período");
		}

		return funcionarioMapper.listEntityToDTO(funcionarios);
	}

	public List<FuncionarioProjecao> buscarFuncionarioMaiorSalario() {
		log.info("Busca por funcionário que possui maior salário");
		var funcionarios = funcionarioRepository.buscarFuncionarioMaiorSalario();
		if (funcionarios.isEmpty()) {
			throw new ObjNaoEncontradoException("Nenhum resultado encontratado");
		}

		return funcionarios;
	}

	public Page<FuncionarioModelResponse> buscarFuncionarioPorNome(String nome) {
		log.info("Busca funcionário por nome {}", nome);
		Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending().and(Sort.by("nome").ascending()));
		var pageFuncionarios = funcionarioRepository.findAll(Specification.where(FuncionarioSpecification.nome(nome)), pageable);
		
		if (pageFuncionarios.isEmpty()) {
			throw new ObjNaoEncontradoException("Funcionário de nome não encontratado");
		}

		return funcionarioMapper.mapEntityPageToDTO(pageable, pageFuncionarios);

	}
	
	public Page<FuncionarioModelResponse> buscarFuncionarioSalario(Double salario) {
		log.info("Busca funcionário por salario {}", salario);
		Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending().and(Sort.by("nome").ascending()));
		var pageFuncionarios = funcionarioRepository.findAll(Specification.where(FuncionarioSpecification.salario(salario)), pageable);
		
		if (pageFuncionarios.isEmpty()) {
			throw new ObjNaoEncontradoException("Salário de Funcionário não encontratado");
		}

		return funcionarioMapper.mapEntityPageToDTO(pageable, pageFuncionarios);

	}
	
	
	public Page<FuncionarioModelResponse> filtrarPor(String nome, String cpf, Double salario){
		log.info("Filtrar por nome: {}, CPF: {}, Salário: {}", nome, cpf, salario);
		Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending().and(Sort.by("nome").ascending()));
		
		var pageFuncionario = funcionarioRepository.findAll(Specification
				.where(FuncionarioSpecification.nome(nome))
				.or(Specification.where(FuncionarioSpecification.cpf(cpf)))
				.or(Specification.where(FuncionarioSpecification.salario(salario))), pageable);
		
		if (pageFuncionario.isEmpty()) {
			throw new ObjNaoEncontradoException("Nenhum resultado encontrado para esta busca");
		}
		
		return funcionarioMapper.mapEntityPageToDTO(pageable, pageFuncionario);
	}
	
}
