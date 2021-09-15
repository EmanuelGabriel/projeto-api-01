package br.com.emanuelgabriel.projeto01;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.emanuelgabriel.projeto01.services.json.Post;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProjetoExemploTest02 {

	private static final String URL_BASE_JSON_PLACEHOLDER = "https://jsonplaceholder.typicode.com/posts";

	public static void main(String[] args) throws URISyntaxException {

		var rt = new RestTemplate();
		var resposta = rt.getForObject(URL_BASE_JSON_PLACEHOLDER, String.class);
		log.info("Resp: {}", resposta);
		
		ResponseEntity<String> respEntity = rt.getForEntity(URL_BASE_JSON_PLACEHOLDER, String.class);
		
		String body = respEntity.getBody();
		HttpHeaders headers = respEntity.getHeaders();
		log.info("{}", body);
		log.info("{}", headers);
		
		
		System.out.println("\n");
		
		
		var post = rt.getForObject(URL_BASE_JSON_PLACEHOLDER.concat("/1"), Post.class);
		log.info("Resp: {}", post);
		
		
		System.out.println("\n\n");
		
		
		var request = new RequestEntity<Object>(HttpMethod.GET, new URI(URL_BASE_JSON_PLACEHOLDER.concat("/10"))); 
		
		var postsEntity = rt.exchange(request, Post.class);
		var post1 = postsEntity.getBody();
		
		log.info("{}", post1);
		

	}

}
