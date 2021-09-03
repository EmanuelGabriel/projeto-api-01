package br.com.emanuelgabriel.projeto01.services.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class Teste {

	private static final String URL_BASE_CAMUNDA = "https://internos.tce.pi.gov.br/camunda/";

	@Autowired
	private RestTemplate rt;

	public String getProcessBusinesskey(String businesskey) {
		String urlCadastroDemanda = URL_BASE_CAMUNDA.concat("engine-rest/process-instance?processDefinitionKey=N211AtenderChamadoProcess&businessKey=".concat(businesskey));
		// String resposta = rt.exchange(URI.create(urlCadastroDemanda), HttpMethod.GET, getEntity(), String.class);
		return null;

	}

}
