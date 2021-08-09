package br.com.emanuelgabriel.projeto01.domain.entity.enums;

import lombok.Getter;

@Getter
public enum SituacaoPedido {

	ORAR("Orar"),
	ORANDO("Orando");
	
	private String descricao;
	
	SituacaoPedido(String descricao) {
		this.descricao = descricao;
	}
	
}
