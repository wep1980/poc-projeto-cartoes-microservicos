package br.com.wepdev.mscartoes.infra.repository;

import br.com.wepdev.mscartoes.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    // renda menor ou igual
    List<Cartao> findByRendaLessThanEqual(BigDecimal renda);
}
