package br.com.emanuelgabriel.projeto01.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.emanuelgabriel.projeto01.domain.dto.request.CardoModelRequest;
import br.com.emanuelgabriel.projeto01.domain.dto.response.CargoModelResponse;
import br.com.emanuelgabriel.projeto01.domain.entity.Cargo;
import br.com.emanuelgabriel.projeto01.domain.mapper.CargoMapper;
import br.com.emanuelgabriel.projeto01.domain.repository.CargoRepository;
import br.com.emanuelgabriel.projeto01.services.exception.RegraNegocioException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CargoService {

	@Autowired
	private CargoRepository cargoRepository;

	@Autowired
	private CargoMapper cargoMapper;

	public Page<CargoModelResponse> buscarTodos(Pageable pageable) {
		log.info("Busca todos os cargos");
		// Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
		Page<Cargo> cargos = cargoRepository.findAll(pageable);
		return cargoMapper.mapEntityPageToDTO(pageable, cargos);	
	}

	public CargoModelResponse salvar(CardoModelRequest request) {
		log.info("Salvar cargo {}", request);
		Cargo descricaoExistente = cargoRepository.findByDescricao(request.getDescricao());

		if (descricaoExistente != null && !descricaoExistente.equals(request)) {
			throw new RegraNegocioException("Já existe cargo registrado com este nome");
		}

		Cargo cargo = cargoMapper.dtoToEntity(request);

		return cargoMapper.entityToDTO(cargoRepository.saveAndFlush(cargo));
	}

	public CargoModelResponse atualizar(Long idCargo, Cargo cargo) {
		log.info("Atualiza cargo de código {} e cargo {}", idCargo, cargo);
		return null;
	}
}
