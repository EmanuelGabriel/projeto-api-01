package br.com.emanuelgabriel.projeto01;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProjetoExemploTest {

	public static void main(String[] args) {

		List<String> frutas = Arrays.asList("Mamão", "Manga", "Pêra", "Uva", "Abacate", "Melão", "Banana");
		log.info("Frutas {}", frutas);

		List<String> collect = frutas.stream().filter(f -> f.endsWith("nana")).collect(Collectors.toList());
		log.info("Frutas {}", collect);

		System.out.println("\n\n");

		List<Integer> numeros = Arrays.asList(10, 30, 40, 50, 77, 12, 89, 100, 345, 789, 1200, 99);
		log.info("Números {}", numeros);
		List<Integer> collect2 = numeros.stream()
					 .filter(n -> n > 50)
					 .sorted()
					 .collect(Collectors.toList());

		long count = collect2.stream().count();
		log.info("Contator: {}", count);
		
		log.info("Filtro/Números: {}", collect2);
		

	}

}
