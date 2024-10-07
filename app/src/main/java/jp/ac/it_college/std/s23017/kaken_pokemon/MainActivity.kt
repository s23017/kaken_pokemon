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
        RetrofitInstance.api.getPokemonById(randomPokemonId).enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if (response.isSuccessful) {
                    val pokemon = response.body()
                    textView.text = "ポケモン名: ${pokemon?.name}\nタイプ: ${pokemon?.types?.joinToString { it.type.name }}"
                } else {
                    textView.text = "エラー: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                textView.text = "失敗: ${t.message}"
            }
        })
    }
}