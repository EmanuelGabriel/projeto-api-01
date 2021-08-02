package br.com.emanuelgabriel.projeto01.domain.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.com.emanuelgabriel.projeto01.domain.dto.request.FuncionarioModelRequest;
import br.com.emanuelgabriel.projeto01.domain.dto.response.FuncionarioModelResponse;
import br.com.emanuelgabriel.projeto01.domain.entity.Funcionario;

@Component
public class FuncionarioMapper {

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Responsável em converter uma entitade para dto
	 * 
	 * @param entitade
	 * @return dto
	 */
	public FuncionarioModelResponse entityToDTO(Funcionario funcionario) {
		return modelMapper.map(funcionario, FuncionarioModelResponse.class);
	}

	/**
	 * Responsável por converter dto em entidade
	 * 
	 * @param dto
	 * @return entidade
	 */
	public Funcionario dtoToEntity(FuncionarioModelResponse dto) {
		return this.modelMapper.map(dto, Funcionario.class);
	}

	public Funcionario dtoToEntity(FuncionarioModelRequest request) {
		return this.modelMapper.map(request, Funcionario.class);
	}

	/**
	 * 
	 * @param funcionarios
	 * @return dto
	 */
	public List<FuncionarioModelResponse> listEntityToDTO(List<Funcionario> funcionarios) {
		return funcionarios.stream().map(this::entityToDTO).collect(Collectors.toList());
	}

	public Page<FuncionarioModelResponse> mapEntityPageToDTO(Pageable pageable, Page<Funcionario> pageFuncionarios) {
		List<FuncionarioModelResponse> listDtos = listEntityToDTO(pageFuncionarios.getContent());
		return new PageImpl<>(listDtos, pageable, pageFuncionarios.getTotalElements());
	}

}
