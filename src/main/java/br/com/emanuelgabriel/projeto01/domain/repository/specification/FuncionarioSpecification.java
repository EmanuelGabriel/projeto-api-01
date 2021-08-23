package br.com.emanuelgabriel.projeto01.domain.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import br.com.emanuelgabriel.projeto01.domain.entity.Funcionario;

/**
 * 
 * @author emanuel.sousa
 *
 */
public class FuncionarioSpecification {

	public static Specification<Funcionario> nome(String nome){
		
		if (nome == null) {
			return null;
		}
		
		return (root, criteraQuery, criteriaBuilder) -> 
		     criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%".concat(nome.toLowerCase()).concat("%"));		     
	}
	
	public static Specification<Funcionario> cpf(String cpf){
		
		if (cpf == null) {
			return null;
		}
		
		return (root, criteraQuery, criteriaBuilder) -> 
		     criteriaBuilder.equal(root.get("cpf"), cpf);		     
	}
	
	public static Specification<Funcionario> salario(Double salario){
		
		if (salario == null) {
			return null;
		}
		
		return (root, criteraQuery, criteriaBuilder) -> 
		     criteriaBuilder.greaterThanOrEqualTo(root.get("salario"), salario);		     
	}
}
