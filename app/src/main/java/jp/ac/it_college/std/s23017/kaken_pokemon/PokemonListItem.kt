package jp.ac.it_college.std.s23017.kaken_pokemon

import com.google.gson.annotations.SerializedName

data class PokemonListItem(
    val name: String,
    val url: String,
    var japaneseName: String? = null
)
data class PokemonResult(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)
data class PokemonSpeciesResponse(
    val names: List<NameInfo>,
    val flavor_text_entries: List<FlavorTextEntry>
)
data class NameInfo(
    val name: String,
    val language: LanguageInfo
)
data class FlavorTextEntry(
    val flavor_text: String,
    val language: LanguageInfo
)
data class LanguageInfo(
    val name: String
)
data class TypeResponse(
    val names: List<Name>
)

data class Name(
    val name: String,
    val language: Language
)

data class Language(
    val name: String
)
data class PokemonType(
    val slot: Int,       // ポケモンのタイプ順序（1番目のタイプか、2番目のタイプかを示す）
    val type: Type       // タイプそのものの詳細（名前やURL）
)
data class type(
    val name: String,    // タイプの名前 (例: "fire", "water")
    val url: String      // タイプ情報へのURL
)



