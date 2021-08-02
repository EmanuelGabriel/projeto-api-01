package br.com.emanuelgabriel.projeto01;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.emanuelgabriel.projeto01.domain.entity.Cargo;
import br.com.emanuelgabriel.projeto01.domain.repository.CargoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class ProjetoApi01Application implements CommandLineRunner {

	@Autowired
	private CargoRepository cargoRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjetoApi01Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		var cargoDba = new Cargo();
		cargoDba.setDescricao("Administração de banco de dados");

		var cargoAnalista = new Cargo();
		cargoAnalista.setDescricao("Analista de Sistemas");

		var cargoRh = new Cargo();
		cargoRh.setDescricao("Analista de RH");

		List<Cargo> cargos = Arrays.asList(cargoDba, cargoAnalista, cargoRh);

		var cargoSalvo = cargoRepository.saveAll(cargos);

		log.info("Cargo salvo: {}", cargoSalvo);

	}

}
