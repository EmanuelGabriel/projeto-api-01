package br.com.emanuelgabriel.projeto01.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.emanuelgabriel.projeto01.domain.dto.request.FuncionarioModelRequest;
import br.com.emanuelgabriel.projeto01.domain.dto.response.FuncionarioModelResponse;
import br.com.emanuelgabriel.projeto01.domain.entity.Cargo;
import br.com.emanuelgabriel.projeto01.domain.entity.Funcionario;
import br.com.emanuelgabriel.projeto01.domain.mapper.FuncionarioMapper;
import br.com.emanuelgabriel.projeto01.domain.repository.CargoRepository;
import br.com.emanuelgabriel.projeto01.domain.repository.FuncionarioRepository;
import br.com.emanuelgabriel.projeto01.services.exception.ObjNaoEncontradoException;
import br.com.emanuelgabriel.projeto01.services.exception.RegraNegocioException;
import lombok.extern.slf4j.Slf4j;

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

}
