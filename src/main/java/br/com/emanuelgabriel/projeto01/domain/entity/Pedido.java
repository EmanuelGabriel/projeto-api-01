package br.com.emanuelgabriel.projeto01.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.emanuelgabriel.projeto01.domain.entity.enums.SituacaoPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_pedido_oracao")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pedido_oracao", nullable = false, updatable = false)
	private Long id;

	@Column(name = "descricao", length = 255, nullable = false)
	private String descricao;

	@Column(name = "data_pedido", nullable = false)
	private LocalDateTime dataPedido;
	
	@Column(name = "situacao", length = 10)
	private SituacaoPedido situacao;

	@CreationTimestamp
	@Column(name = "data_cadastro", nullable = false)
	private LocalDateTime dataCadastro;

	@UpdateTimestamp
	@Column(name = "data_atualizacao", nullable = false)
	private LocalDateTime dataAtualizacao;

}
