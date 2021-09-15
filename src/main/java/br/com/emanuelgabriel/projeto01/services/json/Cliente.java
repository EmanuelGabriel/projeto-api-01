package br.com.emanuelgabriel.projeto01.services.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
	
	private Long id;
	private String email;
    private String nome;
    private String telefone;

}
