package br.com.emanuelgabriel.projeto01.domain.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioModelResponse {

	private Long id;
	private String nome;
	private String cpf;
	private Double salario;
	private CargoModelResponse cargo;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataContratacao;

	private FotoModelResponse foto;

	// private List<UnidadeTrabalho> unidadeTrabalhos;

}
