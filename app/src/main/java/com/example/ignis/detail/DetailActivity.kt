package com.example.ignis.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.ignis.R
import com.example.ignis.databinding.ActivityDetailBinding
import com.example.ignis.network.AllApi
import com.example.ignis.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

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
            val sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
            val token = sharedPreferences.getString("access_token", "")
            Log.d("TEST","t : $token")
            allApi.detailedRead("Bearer $token", feedId = id).enqueue(object : Callback<ReadResponse> {
                override fun onResponse(
                    call: Call<ReadResponse>,
                    response: Response<ReadResponse>
                ) {
                    when(response.code()) {
                        200-> {
                            response.body()?.apply {
                                binding.apply {
                                    val width = resources.displayMetrics.widthPixels
                                    ivDetail.layoutParams.height = width
                                    ivDetail.requestLayout()


                                    Glide
                                        .with(baseContext)
                                        .load(image_url)
                                        .apply(RequestOptions.centerCropTransform())
                                        .into(ivDetail)

                                    Log.d("TEST","v $image_url")
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

            var isLiked:Boolean = false

            binding.btnDetailLike.setOnClickListener {
                allApi.addLike("Bearer $token", feedId = id).enqueue(object : Callback<LikeResponse> {
                    override fun onResponse(
                        call: Call<LikeResponse>,
                        response: Response<LikeResponse>
                    ) {
                        when(response.code()) {
                            200 -> {
                                isLiked = !isLiked
                                if(isLiked) {
                                    binding.tvDetailLikeContent.text = "좋아요를 눌렀습니다"
                                    val newColor = ContextCompat.getColor(this@DetailActivity,R.color.primary)
                                    binding.btnDetailLike.backgroundTintList = ColorStateList.valueOf(newColor)
                                    binding.btnDetailLike.setImageResource(R.drawable.baseline_favorite_24_white)
                                } else {
                                    binding.tvDetailLikeContent.text = "터치해서 좋아요 누르기"
                                    val newColor = ContextCompat.getColor(this@DetailActivity,R.color.gray100)
                                    binding.btnDetailLike.backgroundTintList = ColorStateList.valueOf(newColor)
                                    binding.btnDetailLike.setImageResource(R.drawable.baseline_favorite_24_gray)
                                }
                            }

                        }
                    }

                    override fun onFailure(call: Call<LikeResponse>, t: Throwable) {
                        Toast.makeText(baseContext,"서버 에러",Toast.LENGTH_SHORT).show()
                    }
                })
            }

            binding.tvDetailSend.setOnClickListener {
                if(binding.etDetailComment.text.toString().isNotEmpty()) {
                    allApi.comment("Bearer $token", feedId = id, comment = binding.etDetailComment.text.toString()).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            when(response.code()) {
                                200 -> {
                                    binding.rvDetail.adapter?.notifyDataSetChanged()
                                    binding.etDetailComment.text.clear()
                                }
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(baseContext,"서버 에러",Toast.LENGTH_SHORT).show()
                        }

                    })
                }
            }

            binding.etDetailComment.addTextChangedListener( object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                @SuppressLint("ResourceAsColor")
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.d("TEST","x${binding.etDetailComment.text}")
                    if(binding.etDetailComment.text != null) {
                        binding.tvDetailSend.setTextColor(R.color.primary)
                        binding.tvDetailSend.textColors
                    } else {
                        binding.tvDetailSend.setTextColor(R.color.gray300)
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }

            })
        }

    }
}