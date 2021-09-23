package br.com.emanuelgabriel.projeto01.domain.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.emanuelgabriel.projeto01.domain.dto.response.FotoModelResponse;
import br.com.emanuelgabriel.projeto01.domain.entity.Foto;

@Component
public class FotoMapper {

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * @author emanuel.sousa
	 * @param entity
	 * @return dto
	 */
	public FotoModelResponse entityToDTO(Foto entity) {
		return this.modelMapper.map(entity, FotoModelResponse.class);
	}

	/**
	 * @author emanuel.sousa
	 * @param dto
	 * @return entity
	 */
	public Foto dtoToEntity(FotoModelResponse dto) {
		return this.modelMapper.map(dto, Foto.class);
	}

	/**
	 * @author emanuel.sousa
	 * @param fotos
	 * @return
	 */
	public List<FotoModelResponse> listEntityToDTO(List<Foto> fotos) {
		return fotos.stream().map(this::entityToDTO).collect(Collectors.toList());
	}

}
