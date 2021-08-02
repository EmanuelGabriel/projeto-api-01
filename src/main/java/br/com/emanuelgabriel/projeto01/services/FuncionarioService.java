package br.com.emanuelgabriel.projeto01.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.emanuelgabriel.projeto01.domain.dto.response.FuncionarioModelResponse;
import br.com.emanuelgabriel.projeto01.domain.entity.Funcionario;
import br.com.emanuelgabriel.projeto01.domain.mapper.FuncionarioMapper;
import br.com.emanuelgabriel.projeto01.domain.repository.FuncionarioRepository;
import br.com.emanuelgabriel.projeto01.services.exception.ObjNaoEncontradoException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioMapper funcionarioMapper;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	public FuncionarioModelResponse buscarPorId(Long id) {
		log.info("Busca funcionário por ID {}", id);
		Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(id);
		if (!funcionarioOpt.isPresent()) {
			throw new ObjNaoEncontradoException("Funcionário de ID não encontrado");
		}
		return this.funcionarioMapper.entityToDTO(funcionarioOpt.get());

	}
}
