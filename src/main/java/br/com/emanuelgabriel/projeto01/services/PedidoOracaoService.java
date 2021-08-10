package br.com.emanuelgabriel.projeto01.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.emanuelgabriel.projeto01.domain.dto.request.PedidoOracaoModelRequest;
import br.com.emanuelgabriel.projeto01.domain.dto.response.PedidoOracaoModelResponse;
import br.com.emanuelgabriel.projeto01.domain.entity.Pedido;
import br.com.emanuelgabriel.projeto01.domain.entity.enums.SituacaoPedido;
import br.com.emanuelgabriel.projeto01.domain.mapper.PedidoOracaoMapper;
import br.com.emanuelgabriel.projeto01.domain.repository.PedidoOracaoRepository;
import br.com.emanuelgabriel.projeto01.services.exception.ObjNaoEncontradoException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PedidoOracaoService {

	@Autowired
	private PedidoOracaoRepository pedidoOracaoRepository;

	@Autowired
	private PedidoOracaoMapper pedidoOracaoMapper;

	public PedidoOracaoModelResponse salvar(PedidoOracaoModelRequest request) {
		log.info("Cria um pedido de oração: {}", request);

		var pedido = pedidoOracaoMapper.dtoToEntity(request);
		pedido.setDataPedido(LocalDateTime.now());
		pedido.setDataCadastro(LocalDateTime.now());
		pedido.setSituacao(SituacaoPedido.ORAR);

		return pedidoOracaoMapper.entityToDTO(pedidoOracaoRepository.save(pedido));
	}

	public PedidoOracaoModelResponse buscarPorID(Long idPedido) {
		log.info("Busca pedido de oração por seu ID: {}", idPedido);
		Optional<Pedido> pedidoOracaoOpt = pedidoOracaoRepository.findById(idPedido);
		if (!pedidoOracaoOpt.isPresent()) {
			throw new ObjNaoEncontradoException("Pedido de Oração de ID não encontrado");
		}
		return pedidoOracaoMapper.entityToDTO(pedidoOracaoOpt.get());
	}

	public Page<PedidoOracaoModelResponse> buscarTodosPedidos() {
		log.info("Busca todos os pedidos de oração que foram realizados");
		Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending().and(Sort.by("dataPedido").descending()));
		Page<Pedido> pedidos = pedidoOracaoRepository.findAll(pageable);
		return pedidoOracaoMapper.mapEntityPageToDTO(pageable, pedidos);
	}
	
	
	public PedidoOracaoModelResponse atualizarStatusOracao(Long idPedido) {
	   log.info("Atualiza o status da oração para {}");	
	   return pedidoOracaoMapper.entityToDTO(null);
	}
}
