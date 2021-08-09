package br.com.emanuelgabriel.projeto01.domain.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.com.emanuelgabriel.projeto01.domain.dto.request.PedidoOracaoModelRequest;
import br.com.emanuelgabriel.projeto01.domain.dto.response.PedidoOracaoModelResponse;
import br.com.emanuelgabriel.projeto01.domain.entity.Pedido;

@Component
public class PedidoOracaoMapper {

	@Autowired
	private ModelMapper modelMapper;

	public PedidoOracaoModelResponse entityToDTO(Pedido pedido) {
		return modelMapper.map(pedido, PedidoOracaoModelResponse.class);
	}

	public Pedido dtoToEntity(PedidoOracaoModelResponse dto) {
		return this.modelMapper.map(dto, Pedido.class);
	}

	public Pedido dtoToEntity(PedidoOracaoModelRequest request) {
		return this.modelMapper.map(request, Pedido.class);
	}

	public List<PedidoOracaoModelResponse> listEntityToDTO(List<Pedido> pedidos) {
		return pedidos.stream().map(this::entityToDTO).collect(Collectors.toList());
	}

	public Page<PedidoOracaoModelResponse> mapEntityPageToDTO(Pageable pageable, Page<Pedido> pagePedidos) {
		List<PedidoOracaoModelResponse> listDtos = listEntityToDTO(pagePedidos.getContent());
		return new PageImpl<>(listDtos, pageable, pagePedidos.getTotalElements());
	}
}
