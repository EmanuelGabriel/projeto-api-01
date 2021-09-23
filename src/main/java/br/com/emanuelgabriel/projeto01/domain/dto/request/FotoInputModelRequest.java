
package br.com.emanuelgabriel.projeto01.domain.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FotoInputModelRequest {

	@NotBlank(message = "ID da imagem não pode ser vazio")
	private Long id;
}
