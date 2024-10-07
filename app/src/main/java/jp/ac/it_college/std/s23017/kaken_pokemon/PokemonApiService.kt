package jp.ac.it_college.std.s23017.kaken_pokemon

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiService {

    @GET("pokemon/{id}")
    fun getPokemonById(@Path("id") id: Int): Call<Pokemon>

    // ポケモンの種別情報を取得する（名前、フレーバーテキストなど）
    @GET("pokemon-species/{id}")
    fun getPokemonSpecies(@Path("id") id: String): Call<PokemonSpeciesResponse>

    // タイプ情報を取得する（タイプ名を日本語で取得）
    @GET("type/{name}")
    fun getTypeInfo(@Path("name") name: String): Call<TypeResponse>
}