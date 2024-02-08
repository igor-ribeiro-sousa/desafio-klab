package com.desafio.klab.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.klab.entity.Jogo;

public interface JogoRepository extends JpaRepository<Jogo, Long>
{

}
