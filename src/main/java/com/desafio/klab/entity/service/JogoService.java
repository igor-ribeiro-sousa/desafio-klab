package com.desafio.klab.entity.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.klab.entity.Carta;
import com.desafio.klab.entity.Jogador;
import com.desafio.klab.entity.Jogo;
import com.desafio.klab.entity.repository.JogadorRepository;
import com.desafio.klab.entity.repository.JogoRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Service
public class JogoService
{

   private static final int QUANTIDADE_JOGADOR = 4;

   private static final int QUANTIDADE_CARTAS =  5;
   
   @Autowired
   private JogadorRepository jogadorRepository;

   @Autowired
   private JogoRepository jogoRepository;

   public Jogo jogar()
   {
      List<Carta> baralho = obterCartasDoBaralho();
      List<Jogador> jogadores = distribuirCartas(baralho);
      String resultado = decidirResultado(jogadores);
      jogadores = substituirValoresCartas(jogadores);
      Jogo jogo = Jogo.builder().jogadores(jogadores).resultado(resultado).build();
      return salvar(jogo);
   }

   public List<Carta> obterCartasDoBaralho()
   {
      try
      {
         String jsonCard = obterBaralhoCompleto();
         return extrairCartasDoBaralho(jsonCard);
      }
      catch (IOException e)
      {
         throw new RuntimeException("Erro ao obter cartas do baralho", e);
      }
   }

   public String obterBaralhoCompleto() throws IOException
   {
      URL url = new URL("https://deckofcardsapi.com/api/deck/new/draw/?count=52");
      URLConnection connection = url.openConnection();
      try (InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)))
      {
         return bufferedReader.lines().collect(Collectors.joining());
      }
   }

   public List<Carta> extrairCartasDoBaralho(String jsonCard)
   {
      Gson gson = new Gson();
      JsonObject jsonObject = gson.fromJson(jsonCard, JsonObject.class);
      JsonArray cardsArray = jsonObject.getAsJsonArray("cards");

      List<Carta> cartas = new ArrayList<>();

      for (JsonElement cardElement : cardsArray)
      {
         JsonObject cardObject = cardElement.getAsJsonObject();
         String value = cardObject.get("value").getAsString();

         Carta carta = new Carta();
         carta.setValor(value);
         cartas.add(carta);
      }
      return cartas;
   }

   public List<Jogador> distribuirCartas(List<Carta> baralho)
   {
      List<Jogador> jogadores = new ArrayList<>();

      for (int i = 0; i < QUANTIDADE_JOGADOR; i++)
      {
         List<Carta> mao = new ArrayList<>();

         for (int j = 0; j < QUANTIDADE_CARTAS; j++)
         {
            mao.add(baralho.remove(0));
         }

         String cartasString = mao.stream().map(carta -> String.valueOf(obterValorCarta(carta))).collect(Collectors.joining(","));
         Jogador jogador = Jogador.builder().cartas(cartasString).build();
         jogadores.add(jogador);
      }

      return jogadores;
   }

   public int obterValorCarta(Carta carta)
   {
      switch (carta.getValor())
      {
      case "ACE":
         return 1;
      case "KING":
         return 13;
      case "QUEEN":
         return 12;
      case "JACK":
         return 11;
      default:
         return Integer.parseInt(carta.getValor());
      }
   }

   public String decidirResultado(List<Jogador> jogadores)
   {
      int maiorPontuacao = Integer.MIN_VALUE;
      List<Integer> vencedores = new ArrayList<>();

      for (int i = 0; i < jogadores.size(); i++)
      {
         Jogador jogador = jogadores.get(i);
         int pontuacao = calcularSomatoriaJogador(jogador.getCartas());

         if (pontuacao > maiorPontuacao)
         {
            maiorPontuacao = pontuacao;
            vencedores.clear();
            vencedores.add(i + 1);
         }
         else if (pontuacao == maiorPontuacao)
         {
            vencedores.add(i + 1);
         }
      }
      StringBuilder resultado = new StringBuilder();
      if (vencedores.size() == 1)
      {
         resultado.append("Vencedor é Jogador ").append(vencedores.get(0)).append(" com ").append(maiorPontuacao).append(" pontos");
      }
      else if (vencedores.size() == jogadores.size())
      {
         resultado.append("Empate entre todos os jogadores com ").append(maiorPontuacao).append(" pontos");
      }
      else
      {
         resultado.append("Empate entre os Jogadores ").append(vencedores).append(" com ").append(maiorPontuacao).append(" pontos");
      }

      return resultado.toString();
   }

   public int calcularSomatoriaJogador(String cartasString)
   {
      String[] cartas = cartasString.split(",");
      int somatoria = 0;
      for (String carta : cartas)
      {
         somatoria += Integer.parseInt(carta);
      }
      return somatoria;
   }

   public List<Jogador> substituirValoresCartas(List<Jogador> jogadores)
   {
      List<Jogador> jogadoresModificados = new ArrayList<>();

      for (Jogador jogador : jogadores)
      {
         String[] cartas = jogador.getCartas().split(",");

         for (int i = 0; i < cartas.length; i++)
         {
            String valorOriginal = cartas[i];
            String valorSubstituido = substituirValorCarta(valorOriginal);
            cartas[i] = valorSubstituido;
         }

         String novaCartasString = String.join(",", cartas);
         Jogador jogadorModificado = Jogador.builder().cartas(novaCartasString).build();
         jogadoresModificados.add(jogadorModificado);
      }
      return jogadoresModificados;
   }

   public String substituirValorCarta(String valor)
   {
      switch (valor)
      {
      case "1":
         return "A";
      case "13":
         return "K";
      case "12":
         return "Q";
      case "11":
         return "J";
      default:
         return valor;
      }
   }
   
   public Jogo salvar(Jogo jogo) {
      jogadorRepository.saveAll(jogo.getJogadores());
      return jogoRepository.save(jogo);
  }


}
