package io.github.msj.mspessoa.repository;

import io.github.msj.mspessoa.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    @Override
    long count();

    boolean existsByCpf(String cpf);

    Optional<Pessoa> findByCpf(String cpf);
}
