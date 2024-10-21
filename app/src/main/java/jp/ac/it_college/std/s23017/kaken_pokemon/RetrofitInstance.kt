package jp.ac.it_college.std.s23017.kaken_pokemon

import jp.ac.it_college.std.s23017.kaken_pokemon.model.PokeApiService

object RetrofitInstance {
    private val retrofit by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
    }

    val apiService: PokeApiService by lazy {
        retrofit.create(PokeApiService::class.java)
    }
}
