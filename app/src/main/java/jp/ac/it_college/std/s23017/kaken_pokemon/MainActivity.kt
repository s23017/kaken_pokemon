package jp.ac.it_college.std.s23017.kaken_pokemon

import jp.ac.it_college.std.s23017.kaken_pokemon.model.PokemonSpecies
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jp.ac.it_college.std.s23017.kaken_pokemon.model.Pokemon
import jp.ac.it_college.std.s23017.kaken_pokemon.model.TypeRelationMap
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var typeRelations: TypeRelationMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TextViewの参照を取得
        textView = findViewById(R.id.textView)

        // タイプ相性のデータを読み込む
        typeRelations = loadTypeRelationsFromAssets()

        // ランダムなポケモンIDを設定 (例: 1 ~ 100 の範囲)
        val randomPokemonId = (1..100).random()
        fetchPokemonDetails(randomPokemonId)
    }

    // ポケモンの詳細情報を取得する関数
    private fun fetchPokemonDetails(pokemonId: Int) {
        val apiService = RetrofitInstance.apiService
        val call = apiService.getPokemonDetails(pokemonId)

        call.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if (response.isSuccessful) {
                    val pokemon = response.body()
                    val pokemonType = pokemon?.types?.firstOrNull()?.type?.name // ポケモンの最初のタイプを取得

                    // ポケモンの名前を日本語で取得
                    fetchPokemonSpecies(pokemonId) { speciesName ->
                        // TextViewにポケモンの名前を設定
                        textView.text = "ポケモン名: $speciesName\n"

                        // タイプを日本語で表示
                        textView.append("タイプ: ${pokemon?.types?.joinToString { it.type.name }}\n")

                        // 有利なポケモンの提案
                        pokemonType?.let { suggestStrongPokemon(it) }
                    }
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                textView.text = "エラーが発生しました: ${t.message}"
            }
        })
    }

    // ポケモンの種別情報を取得して日本語名を表示する関数
    private fun fetchPokemonSpecies(pokemonId: Int, callback: (String?) -> Unit) {
        val apiService = RetrofitInstance.apiService
        val call = apiService.getPokemonSpecies(pokemonId)

        call.enqueue(object : Callback<PokemonSpecies> {
            override fun onResponse(call: Call<PokemonSpecies>, response: Response<PokemonSpecies>) {
                if (response.isSuccessful) {
                    val species = response.body()
                    // 日本語名をコールバックで返す
                    callback(species?.names?.find { it.language.name == "ja" }?.name)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<PokemonSpecies>, t: Throwable) {
                textView.text = "エラーが発生しました: ${t.message}"
                callback(null)
            }
        })
    }

    // タイプ相性データを読み込む関数
    private fun loadTypeRelationsFromAssets(): TypeRelationMap {
        val assetManager = assets
        val inputStream = assetManager.open("type_relations.json")
        val reader = InputStreamReader(inputStream)
        val gson = Gson()
        val type = object : TypeToken<TypeRelationMap>() {}.type
        return gson.fromJson(reader, type)
    }

    // 相手のポケモンに有利なポケモンを提案する関数
    private fun suggestStrongPokemon(opponentType: String) {
        // opponentTypeに対して有利なタイプを取得
        val advantageousTypes = when (opponentType) {
            "normal" -> typeRelations.normal.weaknesses
            "fighting" -> typeRelations.fighting.weaknesses
            "flying" -> typeRelations.flying.weaknesses
            "poison" -> typeRelations.poison.weaknesses
            "ground" -> typeRelations.ground.weaknesses
            "rock" -> typeRelations.rock.weaknesses
            "bug" -> typeRelations.bug.weaknesses
            "ghost" -> typeRelations.ghost.weaknesses
            "steel" -> typeRelations.steel.weaknesses
            "fire" -> typeRelations.fire.weaknesses
            "water" -> typeRelations.water.weaknesses
            "grass" -> typeRelations.grass.weaknesses
            "electric" -> typeRelations.electric.weaknesses
            "psychic" -> typeRelations.psychic.weaknesses
            "ice" -> typeRelations.ice.weaknesses
            "dragon" -> typeRelations.dragon.weaknesses
            "fairy" -> typeRelations.fairy.weaknesses
            "dark" -> typeRelations.dark.weaknesses
            // ここに他のタイプを追加する
            else -> emptyList()
        }

        // 有利なタイプが存在する場合
        if (advantageousTypes.isNotEmpty()) {
            textView.append("\n対戦相手のポケモンに有利なタイプ: ${advantageousTypes.joinToString(", ")}")
        } else {
            textView.append("\n有利なタイプが見つかりませんでした。")
        }
    }

}
