package com.example.ignis.profile

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ignis.databinding.ActivityProfileBinding
import com.example.ignis.network.AllApi
import com.example.ignis.network.RetrofitClient
import com.example.ignis.userInfo.UserInfoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val retrofit: Retrofit = RetrofitClient.getInstance()
    private val allApi: AllApi = retrofit.create(AllApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivProfileBack.setOnClickListener { finish() }
        val sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("access_token", "")
        allApi.userInfo(authorization = "Bearer $token")
            .enqueue(object : Callback<UserInfoResponse> {
                override fun onResponse(
                    call: Call<UserInfoResponse>,
                    response: Response<UserInfoResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            response.body()?.apply {
                                binding.apply {
                                    Glide
                                        .with(baseContext)
                                        .load(profile_url)
                                        .into(ivProfile)
                                    tvProfileName.text = user_name
                                    tvProfileAge.text = "$age 세"
                                    tvProfileEmail.text = email
                                    btnProfilePoint.text = "$point 포인트"
                                    tvProfileNameContent.text = user_name

                                    rvProfile.adapter = ProfileAdapter(feeds, baseContext)
                                    rvProfile.layoutManager = LinearLayoutManager(baseContext)
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                    Toast.makeText(baseContext, "서버 실패", Toast.LENGTH_SHORT).show()
                }

            })
    }
}