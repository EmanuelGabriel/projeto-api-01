package br.com.emanuelgabriel.projeto01.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_cargo")
public class Cargo extends EntidadeBase {

	private static final long serialVersionUID = 1L;

	@Column(name = "descricao", length = 100, nullable = false)
	private String descricao;

}
