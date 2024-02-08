package com.desafio.klab.controller;

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
import com.desafio.klab.service.JogoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/jogo")
@Tag(name = "Desafio Técnico Backend PL KLAB", description = "Api Desafio Cartas.")
public class JogoController
{

   @Autowired
   private JogoService jogoService;

   @Operation(summary = "Iniciar um novo jogo")
   @PostMapping("/iniciar")
   public ResponseEntity<JogoDTO> iniciar()
   {
      Jogo jogo = jogoService.jogar();
      JogoDTO jogoDTO = JogoDTO.convertDTO(jogo);
      return ResponseEntity.ok().body(jogoDTO);
   }

   @Operation(summary = "Buscar um jogo pelo ID")
   @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Jogo encontrado"),
   @ApiResponse(responseCode = "404", description = "Jogo não encontrado") })
   @GetMapping("/{id}")
   public ResponseEntity<JogoDTO> buscarPorId(@PathVariable Long id)
   {
      Jogo jogo = jogoService.buscarJogoPorId(id);
      JogoDTO jogoDTO = JogoDTO.convertDTO(jogo);
      return ResponseEntity.ok().body(jogoDTO);
   }

   @Operation(summary = "Buscar todos os jogos")
   @GetMapping
   public ResponseEntity<List<JogoDTO>> buscarTodos()
   {
      List<Jogo> jogo = jogoService.buscarTodos();
      List<JogoDTO> jogosDTO = jogo.stream().map(JogoDTO::convertDTO).collect(Collectors.toList());
      return ResponseEntity.ok().body(jogosDTO);
   }
}
