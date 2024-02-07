package com.desafio.klab.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.desafio.klab.entity.Carta;
import com.desafio.klab.entity.service.JogoService;

@ExtendWith(MockitoExtension.class)
public class JogoServiceTeste
{

   @InjectMocks
   private JogoService jogoService;

   @Test
   public void testQuantidadeCartasDoBaralho() throws IOException
   {
      JogoService jogoService = new JogoService();
      List<Carta> cartas = jogoService.obterCartasDoBaralho();
      assertEquals(52, cartas.size());
   }

   @Test
   public void testExtrairCartasDoBaralho()
   {
      JogoService jogoService = new JogoService();
      String jsonCard = "{\"cards\":[{\"value\":\"A\"},{\"value\":\"2\"},{\"value\":\"3\"}]}";
      List<Carta> cartas = jogoService.extrairCartasDoBaralho(jsonCard);

      assertEquals(3, cartas.size());
      assertEquals("A", cartas.get(0).getValor());
      assertEquals("2", cartas.get(1).getValor());
      assertEquals("3", cartas.get(2).getValor());
   }

}
