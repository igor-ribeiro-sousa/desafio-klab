package com.desafio.klab.entity;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Baralho
{
   private List<Carta> cartas;
}
