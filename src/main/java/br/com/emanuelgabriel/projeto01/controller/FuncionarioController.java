package br.com.emanuelgabriel.projeto01.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.emanuelgabriel.projeto01.domain.dto.request.FuncionarioModelRequest;
import br.com.emanuelgabriel.projeto01.domain.dto.response.FuncionarioModelResponse;
import br.com.emanuelgabriel.projeto01.domain.repository.customers.FuncionarioProjecao;
import br.com.emanuelgabriel.projeto01.services.FuncionarioService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/v1/funcionarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class FuncionarioController {

	@Autowired
	private FuncionarioService funcionarioService;

	@GetMapping(value = "{id}")
	public ResponseEntity<FuncionarioModelResponse> buscarPorId(@PathVariable Long id) {
		log.info("GET /funcionarios/{}", id);
		FuncionarioModelResponse funcionario = funcionarioService.buscarPorId(id);
		return funcionario != null ? ResponseEntity.ok().body(funcionario) : ResponseEntity.notFound().build();
	}
	

	@GetMapping(value = "{cpf}/cpf")
	public ResponseEntity<FuncionarioModelResponse> buscarPoCPF(@PathVariable String cpf) {
		log.info("GET /funcionarios/{}/cpf", cpf);
		FuncionarioModelResponse funcionario = funcionarioService.buscarPorCPF(cpf);
		return funcionario != null ? ResponseEntity.ok().body(funcionario) : ResponseEntity.notFound().build();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<FuncionarioModelResponse> criar(@RequestBody FuncionarioModelRequest request) {
		log.info("POST /funcionarios - body {}", request);
		FuncionarioModelResponse response = this.funcionarioService.salvar(request);
		URI location = getUri(response.getId());
		return ResponseEntity.created(location).build();
	}

	@GetMapping
	public ResponseEntity<Page<FuncionarioModelResponse>> buscarTodos() {
		log.info("GET /funcionarios");
		Page<FuncionarioModelResponse> funcionarios = funcionarioService.buscarTodos();
		return funcionarios != null ? ResponseEntity.ok().body(funcionarios) : ResponseEntity.ok().build();
	}
	
	@GetMapping(value = "maior-salario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FuncionarioModelResponse>> buscarPor(@RequestParam(value = "nome") String nome, @RequestParam(value = "salario") Double salario, 
			@RequestParam(value = "dataContratacao") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataContratacao) {
		log.info("GET /funcionarios/maior-salario {} - {} - {}", nome, salario, dataContratacao);
		List<FuncionarioModelResponse> funcionarios = funcionarioService.buscarPorMaiorSalarioDataContratacao(nome, salario, dataContratacao);
		return funcionarios != null ? ResponseEntity.ok().body(funcionarios) : ResponseEntity.ok().build();
	}
	
	@GetMapping(value = "por-cargo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FuncionarioModelResponse>> buscarPor(@RequestParam(value = "nomeCargo") String nomeCargo) {
		log.info("GET /funcionarios/por-cargo {}", nomeCargo);
		List<FuncionarioModelResponse> funcionarios = funcionarioService.buscarPorCargo(nomeCargo);
		return funcionarios != null ? ResponseEntity.ok().body(funcionarios) : ResponseEntity.ok().build();
	}
	
	@GetMapping(value = "data-contratacao", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FuncionarioModelResponse>> buscarPorDataContratacao(
			@RequestParam(value = "dataContratacao") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataContratacao) {
		log.info("GET /funcionarios/data-contratacao {}", dataContratacao);
		List<FuncionarioModelResponse> funcionarios = funcionarioService.buscarPorDataContratacao(dataContratacao);
		return funcionarios != null ? ResponseEntity.ok().body(funcionarios) : ResponseEntity.ok().build();
	}
	
	@GetMapping(value = "periodo-data-contratacao", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FuncionarioModelResponse>> buscarPorPeriodoDataContratacao(
			@RequestParam(value = "dataInicial") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial, 
			@RequestParam(value = "dataFinal") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
		log.info("GET /funcionarios/periodo-data-contratacao {} a {}", dataInicial, dataFinal);
		List<FuncionarioModelResponse> funcionarios = funcionarioService.buscarPorPeriodoDataContratacao(dataInicial, dataFinal);
		return funcionarios != null ? ResponseEntity.ok().body(funcionarios) : ResponseEntity.ok().build();
	}
	
	@GetMapping(value = "maior-salario-projecao", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FuncionarioProjecao>> buscarPorMaiorSalario() {
		log.info("GET /funcionarios/maior-salario-projecao");
		List<FuncionarioProjecao> funcionarios = funcionarioService.buscarFuncionarioMaiorSalario();
		return funcionarios != null ? ResponseEntity.ok().body(funcionarios) : ResponseEntity.ok().build();
	}
	
	@GetMapping(value = "por-nome", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<FuncionarioModelResponse>> buscarFuncionarioPorNome(@RequestParam("nome") String nome) {
		log.info("GET /funcionarios/por-nome {}", nome);
		Page<FuncionarioModelResponse> funcionarios = funcionarioService.buscarFuncionarioPorNome(nome);
		return funcionarios != null ? ResponseEntity.ok().body(funcionarios) : ResponseEntity.ok().build();
	}
	
	@GetMapping(value = "salario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<FuncionarioModelResponse>> buscarFuncionarioSalario(@RequestParam("salario") Double salario) {
		log.info("GET /funcionarios/salario {}", salario);
		Page<FuncionarioModelResponse> funcionarios = funcionarioService.buscarFuncionarioSalario(salario);
		return funcionarios != null ? ResponseEntity.ok().body(funcionarios) : ResponseEntity.ok().build();
	}
	
	@GetMapping(value = "filtrar-por", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<FuncionarioModelResponse>> filtrarPor(@RequestParam(value = "nome", required = false) String nome, @RequestParam(value = "cpf", required = false) String cpf, @RequestParam(value = "salario", required = false) Double salario) {
		log.info("GET /funcionarios/filtrar-por {}, {}, {}", nome, cpf, salario);
		Page<FuncionarioModelResponse> funcionarios = funcionarioService.filtrarPor(nome, cpf, salario);
		return funcionarios != null ? ResponseEntity.ok().body(funcionarios) : ResponseEntity.ok().build();
	}

	private URI getUri(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
	}

}
