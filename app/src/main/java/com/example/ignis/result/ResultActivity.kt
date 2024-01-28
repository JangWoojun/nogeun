package com.example.ignis.result

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ignis.R
import com.example.ignis.databinding.ActivityResultBinding
import com.example.ignis.main.SearchResponse
import com.example.ignis.network.AllApi
import com.example.ignis.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private val retrofit: Retrofit = RetrofitClient.getInstance()
    private val allApi: AllApi = retrofit.create(AllApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.linearResult.setOnClickListener { finish() }
        val title = intent.extras?.getString("title")

        val sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("access_token", "")
        allApi.getFeed(
            authorization = "Bearer $token",
            title = title!!
        ).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                when(response.code()) {
                    200 -> {
                        binding.tvResultResult.text = "'$title'에 대한 검색 결과 "
                        binding.tvDetailSearchNum.text = response.body()!!.count.toString()

                        binding.rvResult.adapter = ResultAdapter(response.body()!!.feeds,baseContext)
                        binding.rvResult.layoutManager = LinearLayoutManager(baseContext)
                    }
                }

            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Toast.makeText(baseContext,"서버 에러", Toast.LENGTH_SHORT).show()
                Log.d("TEST","e$t")
            }
        })
    }
}