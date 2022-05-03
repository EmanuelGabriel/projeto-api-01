package br.com.emanuelgabriel.projeto01.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioModelRequest {

    @NotBlank(message = "O campo nome não pode ser vazio")
    private String nome;

    private String cpf;

    private Double salario;

    @NotNull(message = "O campo cargo não pode ser nulo")
    private CargoModelInputRequest cargo;

    @NotNull(message = "O campo foto não pode ser nulo")
    private FotoInputModelRequest foto;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataContratacao;

}
