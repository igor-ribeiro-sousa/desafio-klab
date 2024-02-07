package com.desafio.klab.entity.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.klab.entity.Jogo;
import com.desafio.klab.entity.DTO.JogoDTO;
import com.desafio.klab.entity.service.JogoService;

@RestController
@RequestMapping("/jogo")
public class JogoController
{

   @Autowired
   private JogoService jogoService;

   @PostMapping("/iniciar")
   public ResponseEntity<JogoDTO> iniciar()
   {
      Jogo jogo = jogoService.jogar();
      JogoDTO jogoDTO = JogoDTO.convertDTO(jogo);
      return ResponseEntity.ok().body(jogoDTO);
   }

   @GetMapping("/{id}")
   public ResponseEntity<JogoDTO> buscarPorId(@PathVariable Long id)
   {
      Jogo jogo = jogoService.buscarJogoPorId(id);
      JogoDTO jogoDTO = JogoDTO.convertDTO(jogo);
      return ResponseEntity.ok().body(jogoDTO);
   }

   @GetMapping
   public ResponseEntity<List<JogoDTO>> buscarTodos()
   {
      List<Jogo> jogo = jogoService.buscarTodos();
      List<JogoDTO> jogosDTO = jogo.stream().map(JogoDTO::convertDTO).collect(Collectors.toList());
      return ResponseEntity.ok().body(jogosDTO);
   }
}
