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

import org.springframework.stereotype.Service;

import com.desafio.klab.entity.Carta;
import com.desafio.klab.entity.Jogo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Service
public class JogoService
{

   public Jogo jogar()
   {
      List<Carta> baralho = obterCartasDoBaralho();
      return null;
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

}
