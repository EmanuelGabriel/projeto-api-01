package br.com.emanuelgabriel.projeto01.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_foto")
public class Foto extends EntidadeBase {

	private static final long serialVersionUID = 1L;

	@Column(name = "nome_arquivo", length = 100, nullable = false)
	private String nome;

	@Column(name = "type", nullable = false)
	private String type;

	@Column(name = "tamanho_arquivo", length = 40)
	private long tamanho;

	@CreationTimestamp
	@Column(name = "data_cadastro", nullable = false)
	private LocalDateTime dataCadastro;

	@UpdateTimestamp
	@Column(name = "data_atualizacao", nullable = false)
	private LocalDateTime dataAtualizacao;

	@Lob
	@Column(name = "dados", length = 1000)
	private byte[] dados;

}
