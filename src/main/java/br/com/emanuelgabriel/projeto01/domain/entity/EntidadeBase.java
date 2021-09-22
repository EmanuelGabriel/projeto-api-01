package br.com.emanuelgabriel.projeto01.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class EntidadeBase implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "VERSAO")
	private long versao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_ULT_ALTERACAO")
	private LocalDateTime dataUltimaAlteracao;

	@Column(name = "USUARIO_ULT_ALTERACAO")
	private String usuarioUltimaAlteracao;

}
