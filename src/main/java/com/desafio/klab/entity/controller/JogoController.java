package com.desafio.klab.entity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.klab.entity.Jogo;
import com.desafio.klab.entity.DTO.JogoDTO;
import com.desafio.klab.entity.service.JogoService;

@RestController
@RequestMapping("/jogo")
public class JogoController {

    @Autowired
    private JogoService jogoService;

    @PostMapping("/iniciar")
    public ResponseEntity<JogoDTO> iniciar() {
        Jogo jogo = jogoService.jogar();
        JogoDTO jogoDTO = JogoDTO.convertDTO(jogo);
        return ResponseEntity.ok().body(jogoDTO);
    }
}
