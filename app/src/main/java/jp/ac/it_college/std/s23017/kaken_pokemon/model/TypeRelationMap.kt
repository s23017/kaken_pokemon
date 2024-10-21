package jp.ac.it_college.std.s23017.kaken_pokemon.model

data class TypeRelationMap(
    val normal: TypeRelations,
    val fighting: TypeRelations,
    val flying: TypeRelations,
    val poison: TypeRelations,
    val ground: TypeRelations,
    val rock: TypeRelations,
    val bug: TypeRelations,
    val ghost: TypeRelations,
    val steel: TypeRelations,
    val fire: TypeRelations,
    val water: TypeRelations,
    val grass: TypeRelations,
    val electric: TypeRelations,
    val psychic: TypeRelations,
    val ice: TypeRelations,
    val dragon: TypeRelations,
    val fairy: TypeRelations,
    val dark: TypeRelations,
    // 他のタイプを必要に応じて追加...
)

data class TypeRelations(
    val weaknesses: List<String>,
    val strengths: List<String>,
    val immunities: List<String>
)