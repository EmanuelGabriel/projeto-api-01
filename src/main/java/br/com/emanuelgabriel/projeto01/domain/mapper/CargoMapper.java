package br.com.emanuelgabriel.projeto01.domain.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.com.emanuelgabriel.projeto01.domain.dto.request.CardoModelRequest;
import br.com.emanuelgabriel.projeto01.domain.dto.response.CargoModelResponse;
import br.com.emanuelgabriel.projeto01.domain.entity.Cargo;

@Component
public class CargoMapper {

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Responsável em converter uma entidade para dto
	 * 
	 * @param cargo
	 * @return dto
	 */
	public CargoModelResponse entityToDTO(Cargo cargo) {
		return modelMapper.map(cargo, CargoModelResponse.class);
	}

	public Cargo dtoToEntity(CargoModelResponse dto) {
		return this.modelMapper.map(dto, Cargo.class);
	}

	public Cargo dtoToEntity(CardoModelRequest dto) {
		return this.modelMapper.map(dto, Cargo.class);
	}

	public List<CargoModelResponse> listEntityToDTO(List<Cargo> cargos) {
		return cargos.stream().map(this::entityToDTO).collect(Collectors.toList());
	}

	public List<Cargo> listDtoToEntity(List<CargoModelResponse> listCargos) {
		return listCargos.stream().map(this::dtoToEntity).collect(Collectors.toList());
	}

	public Page<CargoModelResponse> mapEntityPageToDTO(Pageable pageable, Page<Cargo> pageCargos) {
		List<CargoModelResponse> listDtos = listEntityToDTO(pageCargos.getContent());
		return new PageImpl<>(listDtos, pageable, pageCargos.getTotalElements());
	}

}
