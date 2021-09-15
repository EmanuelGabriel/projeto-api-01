package br.com.emanuelgabriel.projeto01.services.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
	
	private Integer userId;
    private Integer id;
    private String title;
    private String body;

}
