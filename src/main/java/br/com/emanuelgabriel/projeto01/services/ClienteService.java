package br.com.emanuelgabriel.projeto01.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.emanuelgabriel.projeto01.services.json.Cliente;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClienteService {

	@Autowired
	private RestTemplate rt;

	@Value("${url.base.json.projeto.quarkus}")
	private String urlBase;

	@Cacheable("buscarCliente")
	public List<Cliente> buscarTodosClientes() throws URISyntaxException {
		log.info("Listar todos os clientes");

		var response = new RequestEntity<Object>(HttpMethod.GET, new URI(urlBase.concat("/clientes")));
		log.info("Body {}", response);

		var entityPost = rt.exchange(response, List.class);

		var listPost = entityPost.getBody();

		return listPost;
	}

	public Cliente criar(Cliente cliente) {
		log.info("Criar um post {}", cliente);

		Cliente salvarCliente = new Cliente();
		salvarCliente.setId(cliente.getId());
		salvarCliente.setEmail(cliente.getEmail());
		salvarCliente.setNome(cliente.getNome());
		salvarCliente.setTelefone(cliente.getTelefone());

		ResponseEntity<Cliente> response = rt.postForEntity(urlBase.concat("/clientes"), salvarCliente, Cliente.class);
		log.info("Body {}", response);
		log.info("Response code:: " + response.getStatusCodeValue());

		var clienteSalvo = response.getBody();

		return clienteSalvo;

	}

}
