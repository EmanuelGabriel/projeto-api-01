package br.com.emanuelgabriel.projeto01.domain.repository.funcionarios;

import br.com.emanuelgabriel.projeto01.domain.dto.response.FuncionarioModelResponse;
import br.com.emanuelgabriel.projeto01.domain.entity.Funcionario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author emanuel.sousa
 */

public class FuncionarioRepositoryImpl implements FuncionarioRepositoryQuery {

    private static final Logger LOG = LoggerFactory.getLogger(FuncionarioRepositoryImpl.class.getName());

    @PersistenceContext
    private EntityManager manager;


    @Override
    public Page<FuncionarioModelResponse> filtro(FuncionarioFiltro filtro, Pageable pageable) {
        LOG.info("Filtro: {}; Page: {}", filtro, pageable);

        CriteriaBuilder builder = this.manager.getCriteriaBuilder();
        CriteriaQuery<FuncionarioModelResponse> criteriaQuery = builder.createQuery(FuncionarioModelResponse.class);
        Root<Funcionario> root = criteriaQuery.from(Funcionario.class);

        criteriaQuery.select(builder.construct(FuncionarioModelResponse.class));

        // ORDENAR POR NOME
        criteriaQuery.orderBy(builder.asc(root.get("nome")));

        Predicate[] predicates = criarRestricaoConsultaFiltro(filtro, builder, root);
        criteriaQuery.where(predicates);

        TypedQuery<FuncionarioModelResponse> query = this.manager.createQuery(criteriaQuery);
        adicionarRestrincoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(filtro));
    }

    /**
     * @param filtro
     * @param builder
     * @param root
     * @return Predicate[]
     */
    private Predicate[] criarRestricaoConsultaFiltro(FuncionarioFiltro filtro, CriteriaBuilder builder, Root<Funcionario> root) {

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

        return predicates.toArray(new Predicate[0]);
    }


    /**
     * @param query
     * @param pageable
     */
    private void adicionarRestrincoesDePaginacao(TypedQuery<FuncionarioModelResponse> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    /**
     * @param filter
     * @return long
     */
    private Long total(FuncionarioFiltro filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Funcionario> root = criteria.from(Funcionario.class);

        Predicate[] predicates = criarRestricaoConsultaFiltro(filter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }


}
