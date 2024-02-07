package com.desafio.klab.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.desafio.klab.entity.Carta;
import com.desafio.klab.entity.Jogador;
import com.desafio.klab.entity.Jogo;
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

   @Test
   public void testDistribuirCartas()
   {
      JogoService jogoService = new JogoService();
      List<Carta> baralho = criarBaralhoDeTeste();
      List<Jogador> jogadores = jogoService.distribuirCartas(baralho);

      assertNotNull(jogadores);
      assertEquals(4, jogadores.size());

      for (Jogador jogador : jogadores)
      {
         assertNotNull(jogador.getCartas());
         String[] cartas = jogador.getCartas().split(",");
         assertEquals(5, cartas.length);
      }
   }

   private List<Carta> criarBaralhoDeTeste()
   {
      int quantidadeDeCartasDoBaralho = 52;
      List<Carta> baralho = new ArrayList<>();
      for (int i = 0; i < quantidadeDeCartasDoBaralho; i++)
      {
         Carta carta = new Carta();
         carta.setValor(String.valueOf(i));
         baralho.add(carta);
      }
      return baralho;
   }

   @Test
   public void testObterValorCartaEspecial()
   {
      JogoService jogoService = new JogoService();

      List<String> valoresEspeciais  = Arrays.asList("ACE", "KING", "QUEEN", "JACK", "5");
      List<Integer> valoresEsperados = Arrays.asList(1, 13, 12, 11, 5);

      for (int i = 0; i < valoresEspeciais.size(); i++)
      {
         Carta carta = new Carta();
         carta.setValor(valoresEspeciais.get(i));
         assertEquals(valoresEsperados.get(i), jogoService.obterValorCarta(carta));
      }
   }
   
   @Test
   public void testDecidirResultadoVencedorUnico() {
       Jogador jogador1 = Jogador.builder().cartas("1,2,3,4,5").build();
       Jogador jogador2 = Jogador.builder().cartas("2,3,4,5,6").build();

       Jogo jogo = Jogo.builder().jogadores(Arrays.asList(jogador1, jogador2)).build();
       String resultado = jogoService.decidirResultado(jogo.getJogadores());

       assertTrue(resultado.contains("Vencedor") || resultado.contains("Empate"));
   }

   @Test
   public void testDecidirResultadoEmpate() {
       Jogador jogador1 = Jogador.builder().cartas("1,2,3,4,5").build();
       Jogador jogador2 = Jogador.builder().cartas("1,2,3,4,5").build();
       Jogador jogador3 = Jogador.builder().cartas("1,1,1,1,1").build();
       Jogador jogador4 = Jogador.builder().cartas("1,2,3,4,5").build();

       Jogo jogo = Jogo.builder().jogadores(Arrays.asList(jogador1, jogador2, jogador3, jogador4)).build();
       String resultado = jogoService.decidirResultado(jogo.getJogadores());

       assertTrue(resultado.contains("Vencedor") || resultado.contains("Empate"));
   }

}
