package jp.ac.it_college.std.s23017.kaken_pokemon.model

data class PokemonType(
    val names: List<PokemonTypeName>
)

data class PokemonTypeName(
    val name: String,
    val language: PokemonLanguage
)
