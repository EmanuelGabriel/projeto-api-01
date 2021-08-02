package br.com.emanuelgabriel.projeto01.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.emanuelgabriel.projeto01.domain.dto.request.CargoModelRequest;
import br.com.emanuelgabriel.projeto01.domain.dto.response.CargoModelResponse;
import br.com.emanuelgabriel.projeto01.services.CargoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/cargos", produces = MediaType.APPLICATION_JSON_VALUE)
public class CargoController {

	@Autowired
	private CargoService cargoService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<CargoModelResponse> criar(@RequestBody CargoModelRequest request) {
		log.info("POST /cargos {}", request);
		CargoModelResponse response = this.cargoService.salvar(request);
		URI location = getUri(response.getId());
		return ResponseEntity.created(location).build();
	}

	@GetMapping
	public ResponseEntity<Page<CargoModelResponse>> buscarTodos(Pageable pageable) {
		log.info("GET /cargos {}", pageable);
		Page<CargoModelResponse> cargos = cargoService.buscarTodos(pageable);
		return cargos != null ? ResponseEntity.ok().body(cargos) : ResponseEntity.ok().build();
	}

	private URI getUri(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
	}

}
