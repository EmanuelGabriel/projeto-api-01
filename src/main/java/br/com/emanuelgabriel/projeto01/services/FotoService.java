package br.com.emanuelgabriel.projeto01.services;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.emanuelgabriel.projeto01.domain.dto.response.FotoModelResponse;
import br.com.emanuelgabriel.projeto01.domain.entity.Foto;
import br.com.emanuelgabriel.projeto01.domain.mapper.FotoMapper;
import br.com.emanuelgabriel.projeto01.domain.repository.FotoRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author emanuel.sousa
 *
 */

@Slf4j
@Service
public class FotoService {

	@Autowired
	private FotoRepository fotoRepository;

	@Autowired
	private FotoMapper mapper;

	public FotoModelResponse upload(MultipartFile file) throws IOException {
		log.info("Faz o upload da imagem do funcionário - FileName: {}", file.getOriginalFilename());

		Foto obj = Foto.builder().nome(file.getOriginalFilename()).type(file.getContentType()).tamanho(file.getSize())
				.dados(file.getBytes()).dataCadastro(LocalDateTime.now()).build();

		fotoRepository.save(obj);

		return this.mapper.entityToDTO(obj);
	}
}
