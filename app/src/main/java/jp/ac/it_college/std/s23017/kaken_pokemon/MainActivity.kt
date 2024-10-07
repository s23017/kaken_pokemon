package jp.ac.it_college.std.s23017.kaken_pokemon

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)

        // ランダムなポケモンIDを生成
        val randomPokemonId = Random.nextInt(1, 152) // 1から151の間のランダムな数を生成

        // APIを呼び出してポケモンデータを取得
        fetchPokemonDetails(randomPokemonId)
    }

    private fun fetchPokemonDetails(pokemonId: Int) {
        // まずポケモンの基本情報を取得
        RetrofitInstance.api.getPokemonById(pokemonId).enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if (response.isSuccessful) {
                    val pokemon = response.body()
//                    // 英語の名前とタイプを一旦表示
//                    textView.text = "ポケモン名: ${pokemon?.name}\nタイプ: ${pokemon?.types?.joinToString { it.type.name }}"

                    // ポケモンの種別情報を取得して日本語名を表示
                    fetchPokemonSpecies(pokemonId)

                    // 各タイプの日本語を取得して表示
                    pokemon?.let {
                        fetchPokemonTypeInJapanese(it.types)
                    }

                } else {
                    textView.text = "エラー: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                textView.text = "失敗: ${t.message}"
            }
        })
    }

    // ポケモンの種別情報から日本語名を取得する
    private fun fetchPokemonSpecies(pokemonId: Int) {
        RetrofitInstance.api.getPokemonSpecies(pokemonId.toString()).enqueue(object : Callback<PokemonSpeciesResponse> {
            override fun onResponse(call: Call<PokemonSpeciesResponse>, response: Response<PokemonSpeciesResponse>) {
                if (response.isSuccessful) {
                    val species = response.body()
                    species?.let {
                        // 日本語の名前を取得
                        val japaneseName = it.names.find { nameInfo ->
                            nameInfo.language.name == "ja-Hrkt"
                        }?.name

                        // 画面に日本語名を表示
                        textView.text = "${textView.text}\n日本語名: $japaneseName"
                    }
                } else {
                    textView.text = "種別データの取得に失敗しました"
                }
            }

            override fun onFailure(call: Call<PokemonSpeciesResponse>, t: Throwable) {
                textView.text = "種別データの取得に失敗: ${t.message}"
            }
        })
    }

    // タイプ情報を日本語で取得して表示する
    private fun fetchPokemonTypeInJapanese(types: List<Type>) {
        types.forEach { type ->
            // 各タイプの詳細情報を取得する（タイプ名を日本語に変換）
            RetrofitInstance.api.getTypeInfo(type.type.name).enqueue(object : Callback<TypeResponse> {
                override fun onResponse(call: Call<TypeResponse>, response: Response<TypeResponse>) {
                    if (response.isSuccessful) {
                        val typeResponse = response.body()
                        typeResponse?.let {
                            // 日本語のタイプ名を取得
                            val japaneseTypeName = it.names.find { nameInfo ->
                                nameInfo.language.name == "ja-Hrkt"
                            }?.name

                            // 日本語のタイプ名を表示
                            textView.text = "${textView.text}\nタイプ: $japaneseTypeName"
                        }
                    }
                }

                override fun onFailure(call: Call<TypeResponse>, t: Throwable) {
                    textView.text = "${textView.text}\nタイプ情報の取得に失敗: ${t.message}"
                }
            })
        }
    }
}
