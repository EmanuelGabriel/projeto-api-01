package br.com.emanuelgabriel.projeto01.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.emanuelgabriel.projeto01.services.json.Post;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostService {

	@Autowired
	private RestTemplate rt;

	@Value("${url.base.json_placeholder}")
	private String urlBase;

	public List<Post> getPosts() throws URISyntaxException {
		log.info("Listar todos os Posts");

		var request = new RequestEntity<Object>(HttpMethod.GET, new URI(urlBase.concat("/posts")));
		log.info("URL: {}", request.getUrl());
		log.info("Request: {}", request);

		var entityPost = rt.exchange(request, List.class);

		var listPost = entityPost.getBody();

		return listPost;
	}

	public Post buscarPorId(Long id) {
		log.info("Buscar post por ID {}", id);

		try {
			
			var request = new RequestEntity<Object>(HttpMethod.GET, new URI(urlBase.concat("/posts/" + id)));
			log.info("Request: {}", request);
			log.info("Request: {}", request.getUrl());

			var postEntity = rt.exchange(request, Post.class);

			var post = postEntity.getBody();

			if (post == null) {
				throw new RestClientException("Post não encontrado");
			}

			return post;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public Post criar(Post post) {
		log.info("Criar um post {}", post);
		
			Post salvarPost = new Post();
			salvarPost.setId(post.getId());
			salvarPost.setTitle(post.getTitle());
			salvarPost.setBody(post.getBody());
			salvarPost.setUserId(post.getUserId());
			
			// HttpEntity<Post> entity = new HttpEntity<Post>(salvarPost);
			//Post postForObject = rt.postForObject(urlBase.concat("/posts"), entity, Post.class);
			
			ResponseEntity<Post> response = rt.postForEntity(urlBase.concat("/posts"), salvarPost, Post.class);
			log.info("Body {}", response.getBody());
			log.info("Response code:: " + response.getStatusCodeValue());
			
			var postSalvo = response.getBody();
			
			return postSalvo;
			
		
	}
}
