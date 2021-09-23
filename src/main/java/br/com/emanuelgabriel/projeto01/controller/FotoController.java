package br.com.emanuelgabriel.projeto01.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.emanuelgabriel.projeto01.domain.dto.response.FotoModelResponse;
import br.com.emanuelgabriel.projeto01.services.FotoService;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author emanuel.sousa
 *
 */

@Slf4j
@RestController
@RequestMapping(value = "/v1/fotos", produces = MediaType.APPLICATION_JSON_VALUE)
public class FotoController {

	@Autowired
	private FotoService fotoService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/upload")
	public ResponseEntity<FotoModelResponse> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		log.info("POST /upload - FileName: '{}' - ContentType: '{}'", file.getOriginalFilename(), file.getContentType());
		return ResponseEntity.ok().body(fotoService.upload(file));
	}

}
