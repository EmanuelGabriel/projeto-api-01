package br.com.emanuelgabriel.projeto01.controller;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.emanuelgabriel.projeto01.services.ClienteService;
import br.com.emanuelgabriel.projeto01.services.json.Cliente;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClienteConsumerController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	public ResponseEntity<List<Cliente>> buscarTodosClientes() throws URISyntaxException {
		log.info("GET /clientes/");
		var clientes = clienteService.buscarTodosClientes();
		return clientes != null ? ResponseEntity.ok().body(clientes) : ResponseEntity.ok().build();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) throws URISyntaxException {
		log.info("POST /clientes/");
		var clienteSalvo = clienteService.criar(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
	}


}
