package com.example.ignis.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ignis.R
import com.example.ignis.databinding.ActivityDetailBinding
import com.example.ignis.network.AllApi
import com.example.ignis.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val retrofit: Retrofit = RetrofitClient.getInstance()
    private val allApi: AllApi = retrofit.create(AllApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivDetailBack.setOnClickListener { finish() }
        val id = intent.extras?.getLong("Id")

        if(id!= null) {
            allApi.detailedRead(feedId = id).enqueue(object : Callback<ReadResponse> {
                override fun onResponse(
                    call: Call<ReadResponse>,
                    response: Response<ReadResponse>
                ) {
                    when(response.code()) {
                        200-> {
                            response.body()?.apply {
                                binding.apply {
                                    Glide
                                        .with(baseContext)
                                        .load(imageUrl)
                                        .into(ivDetail)
                                    tvDetailTitle.text = title
                                    tvDetailName.text = user
                                    tvDetailDate.text = createAt
                                    tvDetailLike.text = "$count 개"

                                    rvDetail.adapter = DetailAdapter(comments,baseContext)
                                    rvDetail.layoutManager = LinearLayoutManager(baseContext)
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ReadResponse>, t: Throwable) {
                    Toast.makeText(baseContext,"서버 에러",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}