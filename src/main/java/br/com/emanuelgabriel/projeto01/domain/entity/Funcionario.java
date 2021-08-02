package br.com.emanuelgabriel.projeto01.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_funcionario")
public class Funcionario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_funcionario", nullable = false, updatable = false)
	private Long id;

	@Column(name = "nome", length = 100, nullable = false)
	private String nome;
	
	@Column(name = "cpf", length = 15, nullable = false)
	private String cpf;
	
	@Column(name = "salario", nullable = false)
	private Double salario;
	
	
	
	
	
	
}
