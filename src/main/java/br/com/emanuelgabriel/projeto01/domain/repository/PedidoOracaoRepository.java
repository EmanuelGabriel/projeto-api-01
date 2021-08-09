package br.com.emanuelgabriel.projeto01.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.emanuelgabriel.projeto01.domain.entity.Pedido;

@Repository
public interface PedidoOracaoRepository extends JpaRepository<Pedido, Long>{

}
