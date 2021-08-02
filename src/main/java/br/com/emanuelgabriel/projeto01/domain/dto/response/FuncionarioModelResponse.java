package br.com.emanuelgabriel.projeto01.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioModelResponse {

	private Long id;
	private String nome;

}
