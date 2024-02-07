package com.desafio.klab.entity;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Carta
{
   @Id
   private String codigo;

   private String valor;
}