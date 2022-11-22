package com.example.g102app.pokeapi;

import com.example.g102app.models.PokemonRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeapiService {
    @GET("pokemon")//obtener la informacion de la url
    Call<PokemonRespuesta> obtenerListaPokemon(@Query("limit") int limit, @Query("offset") int offset);
    //obtener la lista de pokemon para que sea parametizable, son parametros por medio query
    //y el nombre exacto de la api
}
