package br.com.emanuelgabriel.projeto01.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioModelResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String cpf;
	private Double salario;
	private CargoModelResponse cargo;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataContratacao;

	private FotoModelResponse foto;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private LocalDate dataUltimaAlteracao;

}
