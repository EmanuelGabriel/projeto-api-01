package br.com.emanuelgabriel.projeto01.domain.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tb_funcionario")
public class Funcionario extends EntidadeBase {

	private static final long serialVersionUID = 1L;

	@Column(name = "nome", length = 100, nullable = false)
	private String nome;

	@Column(name = "cpf", length = 15, nullable = false)
	private String cpf;

	@Column(name = "salario", nullable = false)
	private Double salario;

	@Column(name = "data_contratacao", nullable = false)
	private LocalDate dataContratacao;

	@ManyToOne
	@JoinColumn(name = "cargo_id", nullable = false)
	private Cargo cargo;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "foto_id")
	private Foto foto;

}
