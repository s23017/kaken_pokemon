package jp.ac.it_college.std.s23017.kaken_pokemon.model

data class PokemonSpecies(
    val names: List<PokemonName>
)

data class PokemonName(
    val name: String,
    val language: PokemonLanguage
)

data class PokemonLanguage(
    val name: String
)