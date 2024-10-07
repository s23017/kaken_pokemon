package jp.ac.it_college.std.s23017.kaken_pokemon

data class Pokemon(
    val id: Int,
    val name: String,
    val types: List<Type>
)

data class Type(
    val slot: Int,
    val type: TypeName
)

data class TypeName(
    val name: String
)