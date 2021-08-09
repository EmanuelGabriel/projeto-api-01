package br.com.emanuelgabriel.projeto01;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.emanuelgabriel.projeto01.domain.entity.Cargo;
import br.com.emanuelgabriel.projeto01.domain.entity.Funcionario;
import br.com.emanuelgabriel.projeto01.domain.repository.CargoRepository;
import br.com.emanuelgabriel.projeto01.domain.repository.FuncionarioRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class ProjetoApi01Application implements CommandLineRunner {

	@Autowired
	private CargoRepository cargoRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;

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
		
		
		// adicionar funcionários
		var fun1 = new Funcionario();
		fun1.setNome("Francisco Alves Bezerra");
		fun1.setCargo(cargoDba);
		fun1.setDataContratacao(LocalDate.of(2020, 2, 10));
		fun1.setSalario(2500.0);
		fun1.setCpf("90309338399");
		
		var fun2 = new Funcionario();
		fun2.setNome("Pedro Alves Cabral");
		fun2.setCargo(cargoAnalista);
		fun2.setDataContratacao(LocalDate.of(2018, 6, 20));
		fun2.setSalario(1570.0);
		fun2.setCpf("04056395422");
		
		
		var fun3 = new Funcionario();
		fun3.setNome("Jonas de Alcântara Montês");
		fun3.setCargo(cargoRh);
		fun3.setDataContratacao(LocalDate.of(2019, 8, 11));
		fun3.setSalario(2650.10);
		fun3.setCpf("45896352877");
		
		
		List<Funcionario> funcionarios = Arrays.asList(fun1, fun2, fun3);

		var funcionarioSalvo = funcionarioRepository.saveAll(funcionarios);
		log.info("Funcionários salvo {}", funcionarioSalvo);
		
		
	}

}
