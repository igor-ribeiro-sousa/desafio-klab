package com.desafio.klab.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.klab.entity.Jogador;

public interface JogadorRepository extends JpaRepository<Jogador, Long>
{

}