package jp.ac.it_college.std.s23017.kaken_pokemon.model

data class Pokemon(
    val name: String,
    val types: List<PokemonTypeWrapper>
)

data class PokemonTypeWrapper(
    val type: PokemonTypeDetails
)

data class PokemonTypeDetails(
    val name: String
)
