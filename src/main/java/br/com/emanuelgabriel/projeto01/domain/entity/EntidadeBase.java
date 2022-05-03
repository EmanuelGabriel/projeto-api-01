package br.com.emanuelgabriel.projeto01.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class EntidadeBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_ULT_ALTERACAO")
    private Date dataUltimaAlteracao = new Date();

    @Column(name = "USUARIO_ULT_ALTERACAO")
    private String usuarioUltimaAlteracao;

}
