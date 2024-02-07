package com.desafio.klab.entity.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.desafio.klab.entity.Jogador;
import com.desafio.klab.entity.Jogo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JogoDTO implements Serializable
{
   private static final long serialVersionUID = 1L;

   private Long codigo;

   private List<Jogador> jogadores;

   private String resultado;

   public static JogoDTO convertDTO(Jogo jogo)
   {
      List<Jogador> jogadores = new ArrayList<>();
      jogadores.addAll(jogo.getJogadores());

      return JogoDTO.builder()
            .codigo(jogo.getCodigo())
            .jogadores(jogadores)
            .resultado(jogo.getResultado())
            .build();
   }

}