package com.example.g102app.models;

import java.util.ArrayList;
//esta clase maneja la respuesta con su informacion de atributos en un arraylist
public class PokemonRespuesta {
    private ArrayList<Pokemon> results;

    public ArrayList<Pokemon> getResults() {
        return results;
    }

    public void setResults (ArrayList<Pokemon> results) {
        this.results = results;
    }
}
