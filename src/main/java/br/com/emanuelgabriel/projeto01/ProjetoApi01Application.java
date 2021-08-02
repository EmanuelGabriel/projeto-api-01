package br.com.emanuelgabriel.projeto01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.emanuelgabriel.projeto01.domain.dto.request.CardoModelRequest;
import br.com.emanuelgabriel.projeto01.services.CargoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class ProjetoApi01Application implements CommandLineRunner {

	@Autowired
	private CargoService cargoService;

	public static void main(String[] args) {
		SpringApplication.run(ProjetoApi01Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		var cargoDba = new CardoModelRequest();
		cargoDba.setDescricao("Administração de banco de dados");

		var cargoSalvo = cargoService.salvar(cargoDba);

		log.info("Cargo salvo: {}", cargoSalvo);

		
	}
	
	

}
