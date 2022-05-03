package br.com.emanuelgabriel.projeto01.domain.repository.funcionarios;

import br.com.emanuelgabriel.projeto01.domain.dto.response.FuncionarioModelResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author emanuel.sousa
 */
public interface FuncionarioRepositoryQuery {

    Page<FuncionarioModelResponse> filtro(FuncionarioFiltro filtro, Pageable pageable);

}
