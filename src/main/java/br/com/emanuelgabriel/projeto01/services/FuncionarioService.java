package br.com.emanuelgabriel.projeto01.services;

import br.com.emanuelgabriel.projeto01.domain.dto.request.FuncionarioModelRequest;
import br.com.emanuelgabriel.projeto01.domain.dto.response.FuncionarioModelResponse;
import br.com.emanuelgabriel.projeto01.domain.entity.Cargo;
import br.com.emanuelgabriel.projeto01.domain.entity.Foto;
import br.com.emanuelgabriel.projeto01.domain.entity.Funcionario;
import br.com.emanuelgabriel.projeto01.domain.mapper.FuncionarioMapper;
import br.com.emanuelgabriel.projeto01.domain.repository.CargoRepository;
import br.com.emanuelgabriel.projeto01.domain.repository.FotoRepository;
import br.com.emanuelgabriel.projeto01.domain.repository.FuncionarioRepository;
import br.com.emanuelgabriel.projeto01.domain.repository.customers.FuncionarioProjecao;
import br.com.emanuelgabriel.projeto01.domain.repository.funcionarios.FuncionarioFiltro;
import br.com.emanuelgabriel.projeto01.domain.repository.specification.FuncionarioSpecification;
import br.com.emanuelgabriel.projeto01.services.exception.ObjNaoEncontradoException;
import br.com.emanuelgabriel.projeto01.services.exception.RegraNegocioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author emanuel.sousa
 */

@Slf4j
@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioMapper funcionarioMapper;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private FotoRepository fotoRepository;

    public FuncionarioModelResponse buscarPorId(Long id) {
        log.info("Busca funcion??rio por ID {}", id);
        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(id);
        if (funcionarioOpt.isEmpty()) {
            throw new ObjNaoEncontradoException("Funcion??rio de ID n??o encontrado");
        }
        return this.funcionarioMapper.entityToDTO(funcionarioOpt.get());

    }

    public FuncionarioModelResponse buscarPorCPF(String cpf) {
        log.info("Busca funcion??rio por CPF {}", cpf);
        Funcionario funcionario = funcionarioRepository.findByCpf(cpf);
        if (funcionario == null) {
            throw new ObjNaoEncontradoException("Funcion??rio de CPF n??o encontrado");
        }
        return this.funcionarioMapper.entityToDTO(funcionario);

    }

    public FuncionarioModelResponse salvar(FuncionarioModelRequest request) {
        log.info("Cria um funcion??rio {}", request);
        Funcionario funcionarioExistente = funcionarioRepository.findByCpf(request.getCpf());
        if (funcionarioExistente != null && !funcionarioExistente.equals(request)) {
            throw new RegraNegocioException("J?? existe funcion??rio registrado com este CPF");
        }

        Optional<Cargo> cargoOpt = cargoRepository.findById(request.getCargo().getId());
        if (cargoOpt.isEmpty()) {
            throw new ObjNaoEncontradoException("ID do cargo n??o foi encontrado");
        }

        Funcionario funcionario = funcionarioMapper.dtoToEntity(request);
        funcionario.setCargo(cargoOpt.get());

        Optional<Foto> fotoOpt = fotoRepository.findById(request.getFoto().getId());
        if (fotoOpt.isEmpty()) {
            throw new ObjNaoEncontradoException("ID da imagem n??o foi encontrada");
        }
        funcionario.setFoto(fotoOpt.get());

        return funcionarioMapper.entityToDTO(funcionarioRepository.save(funcionario));
    }

    public Page<FuncionarioModelResponse> buscarTodos(Pageable pageable) {
        log.info("Busca todos os cargos");
        Page<Funcionario> cargos = funcionarioRepository.findAll(pageable);
        return funcionarioMapper.mapEntityPageToDTO(pageable, cargos);
    }

    public List<FuncionarioModelResponse> buscarPorCargo(String nomeCargo) {
        log.info("Busca funcion??rio por seu cargo {}", nomeCargo);
        List<Funcionario> porCargo = funcionarioRepository.buscarPorCargo(nomeCargo);
        if (porCargo.isEmpty()) {
            throw new ObjNaoEncontradoException("N??o h?? funcion??rios para este cargo");
        }
        return funcionarioMapper.listEntityToDTO(porCargo);
    }

    public List<FuncionarioModelResponse> buscarPorMaiorSalarioDataContratacao(String nome, Double salario,
                                                                               LocalDate dataContratacao) {
        log.info("Busca funcion??rio por {} - {} - {}", nome, salario, dataContratacao);
        List<Funcionario> buscarPor = funcionarioRepository.buscarPorNomeSalarioMaiorDataContratacao(nome, salario,
                dataContratacao);
        if (buscarPor.isEmpty()) {
            throw new ObjNaoEncontradoException("N??o h?? funcion??rios para esta busca");
        }
        return funcionarioMapper.listEntityToDTO(buscarPor);
    }

    public List<FuncionarioModelResponse> buscarPorDataContratacao(LocalDate dataContratacao) {
        log.info("Busca funcion??rio por sua data de contrata????o {}", dataContratacao);
        List<Funcionario> funcionarios = funcionarioRepository.buscarPorDataContratacao(dataContratacao);
        if (funcionarios.isEmpty()) {
            throw new ObjNaoEncontradoException("N??o foi encontrado funcion??rio nesta data de contrata????o");
        }

        return funcionarioMapper.listEntityToDTO(funcionarios);
    }

    public List<FuncionarioModelResponse> buscarPorPeriodoDataContratacao(LocalDate dataInicio, LocalDate dataFinal) {
        log.info("Busca funcion??rio por per??odo de data de contrata????o inicial {} e final {}", dataInicio, dataFinal);
        List<Funcionario> funcionarios = funcionarioRepository.findByDataContratacaoBetween(dataInicio, dataFinal);
        if (funcionarios.isEmpty()) {
            throw new ObjNaoEncontradoException("N??o foi encontrado funcion??rios contratados neste per??odo");
        }

        return funcionarioMapper.listEntityToDTO(funcionarios);
    }

    public List<FuncionarioProjecao> buscarFuncionarioMaiorSalario() {
        log.info("Busca por funcion??rio que possui maior sal??rio");
        var funcionarios = funcionarioRepository.buscarFuncionarioMaiorSalario();
        if (funcionarios.isEmpty()) {
            throw new ObjNaoEncontradoException("Nenhum resultado encontratado");
        }

        return funcionarios;
    }

    public Page<FuncionarioModelResponse> buscarFuncionarioPorNome(String nome) {
        log.info("Busca funcion??rio por nome {}", nome);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending().and(Sort.by("nome").ascending()));
        var pageFuncionarios = funcionarioRepository.findAll(Specification.where(FuncionarioSpecification.nome(nome)),
                pageable);

        if (pageFuncionarios.isEmpty()) {
            throw new ObjNaoEncontradoException("Funcion??rio de nome n??o encontratado");
        }

        return funcionarioMapper.mapEntityPageToDTO(pageable, pageFuncionarios);

    }

    public Page<FuncionarioModelResponse> buscarFuncionarioSalario(Double salario) {
        log.info("Busca funcion??rio por salario {}", salario);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending().and(Sort.by("nome").ascending()));
        var pageFuncionarios = funcionarioRepository
                .findAll(Specification.where(FuncionarioSpecification.salario(salario)), pageable);

        if (pageFuncionarios.isEmpty()) {
            throw new ObjNaoEncontradoException("Sal??rio de Funcion??rio n??o encontratado");
        }

        return funcionarioMapper.mapEntityPageToDTO(pageable, pageFuncionarios);

    }

    /**
     * Filtro
     *
     * @param nome
     * @param cpf
     * @param salario
     * @return page FuncionarioModelResponse
     */
    public Page<FuncionarioModelResponse> filtrarPor(String nome, String cpf, Double salario) {
        log.info("Filtrar por nome: {}, CPF: {}, Sal??rio: {}", nome, cpf, salario);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending().and(Sort.by("nome").ascending()));

        var pageFuncionario = funcionarioRepository.findAll(Specification.where(FuncionarioSpecification.nome(nome))
                .or(Specification.where(FuncionarioSpecification.cpf(cpf)))
                .or(Specification.where(FuncionarioSpecification.salario(salario))), pageable);

        if (pageFuncionario.isEmpty()) {
            throw new ObjNaoEncontradoException("Nenhum resultado encontrado para esta busca");
        }

        return funcionarioMapper.mapEntityPageToDTO(pageable, pageFuncionario);
    }

    public Page<FuncionarioModelResponse> pageFiltro(FuncionarioFiltro filtro, Pageable pageable) {
        log.info("Filtro page: Filtro: {}; Page: {}", filtro, pageable);
        return funcionarioRepository.filtro(filtro, pageable);
    }

    /**
     * Filtro
     * @param filtro
     * @param pageable
     * @return Page<FuncionarioModelResponse>
     */
    public Page<FuncionarioModelResponse> filtrarPorFuncionario(FuncionarioFiltro filtro, Pageable pageable) {
        log.info("Filtar por {};{}", filtro, pageable);

        var pageFuncionario = funcionarioRepository.findAll((Specification<Funcionario>) (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (!ObjectUtils.isEmpty(filtro.getNome())) {
                predicates.add(builder.like(builder.lower(root.get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
            }

            if (!ObjectUtils.isEmpty(filtro.getCpf())) {
                predicates.add(builder.like(builder.lower(root.get("cpf")), "%" + filtro.getCpf().toLowerCase() + "%"));
            }

            if (!ObjectUtils.isEmpty(filtro.getSalario())) {
                predicates.add(builder.equal(root.get("salario"), filtro.getSalario()));
            }

            if (!ObjectUtils.isEmpty(filtro.getDataContratacao())) {
                predicates.add(builder.equal(root.get("dataContratacao"), filtro.getDataContratacao()));
            }


            return builder.and(predicates.toArray(new Predicate[0]));
        }, pageable);

        pageFuncionario.getTotalElements();
        pageFuncionario.getTotalPages();

        return funcionarioMapper.mapEntityPageToDTO(pageable, pageFuncionario);
    }

}
