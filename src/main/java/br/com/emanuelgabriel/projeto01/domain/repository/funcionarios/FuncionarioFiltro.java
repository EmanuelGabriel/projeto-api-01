package br.com.emanuelgabriel.projeto01.domain.repository.funcionarios;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author emanuel.sousa
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioFiltro implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String cpf;
	private Double salario;

	// ISO Date Format yyyy-MM-dd - exemplo: "2000-10-31"
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataContratacao;

}
