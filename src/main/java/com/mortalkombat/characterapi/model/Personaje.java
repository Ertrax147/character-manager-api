package com.mortalkombat.characterapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Personaje {

    private Long id;
    private String nombre;
    private int saludMaxima;
    private NivelPoder nivelDePoder;
    private List<String> movimientosEspeciales;
}