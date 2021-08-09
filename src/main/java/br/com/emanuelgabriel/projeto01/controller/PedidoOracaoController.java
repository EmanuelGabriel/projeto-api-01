package br.com.emanuelgabriel.projeto01.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.emanuelgabriel.projeto01.domain.dto.request.PedidoOracaoModelRequest;
import br.com.emanuelgabriel.projeto01.domain.dto.response.PedidoOracaoModelResponse;
import br.com.emanuelgabriel.projeto01.services.PedidoOracaoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/pedidos-oracao", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoOracaoController {

	@Autowired
	private PedidoOracaoService pedidoOracaoService;

	@GetMapping
	public ResponseEntity<Page<PedidoOracaoModelResponse>> buscarTodosPedidos() {
		log.info("GET /pedidos-oracao");
		Page<PedidoOracaoModelResponse> pedidosResponse = pedidoOracaoService.buscarTodosPedidos();
		return pedidosResponse != null ? ResponseEntity.ok().body(pedidosResponse) : ResponseEntity.ok().build();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<PedidoOracaoModelResponse> criar(@RequestBody PedidoOracaoModelRequest request) {
		log.info("POST /pedidos-oracao - body {}", request);
		PedidoOracaoModelResponse response = this.pedidoOracaoService.salvar(request);
		URI location = getUri(response.getId());
		return ResponseEntity.created(location).build();
	}

	@GetMapping(value = "{idPedido}")
	public ResponseEntity<PedidoOracaoModelResponse> buscarPorId(@PathVariable Long idPedido) {
		log.info("GET /pedidos-oracao/{}", idPedido);
		PedidoOracaoModelResponse pedidoOracao = pedidoOracaoService.buscarPorID(idPedido);
		return pedidoOracao != null ? ResponseEntity.ok().body(pedidoOracao) : ResponseEntity.notFound().build();
	}

	private URI getUri(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
	}
}
