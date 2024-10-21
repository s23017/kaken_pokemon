package jp.ac.it_college.std.s23017.kaken_pokemon.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {
    @GET("pokemon/{id}")
    fun getPokemonDetails(@Path("id") pokemonId: Int): Call<Pokemon>

    @GET("pokemon-species/{id}")
    fun getPokemonSpecies(@Path("id") pokemonId: Int): Call<PokemonSpecies>

    @GET("type/{name}")
    fun getPokemonTypeDetails(@Path("name") typeName: String): Call<PokemonType>

    @GET("type/{type}")
    fun getPokemonByType(@Path("type") type: String): Call<TypePokemonList>
}