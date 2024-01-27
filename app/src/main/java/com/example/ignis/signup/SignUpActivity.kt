package com.example.ignis.signup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ignis.R
import com.example.ignis.databinding.ActivitySignUpBinding
import com.example.ignis.login.LoginRequest
import com.example.ignis.login.LoginResponse
import com.example.ignis.main.MainActivity
import com.example.ignis.network.AllApi
import com.example.ignis.network.RetrofitClient
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SignUpActivity : AppCompatActivity(), NameFragment.NameFragmentListener {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            if (savedInstanceState == null) {
                val nameFragment = NameFragment()
                nameFragment.setListener(this@SignUpActivity)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, nameFragment)
                    .commit()
            }
        }

    }

    override fun onButtonNameClicked(data: String) {
        val ageFragment = AgeFragment.newInstance(data)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, ageFragment)
            .addToBackStack(null)
            .commit()
    }

    fun onButtonAgeClicked(data: SignupRequest) {
        val sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("key", "")
        val retrofitAPI = RetrofitClient.getInstance().create(AllApi::class.java)
        val call: Call<Void> = retrofitAPI.signup("Bearer $token", SignupRequest(data.age, data.user_name))

        Log.d("확인", data.toString())

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("확인", response.body().toString())
                startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("확인", t.toString())
                Toast.makeText(this@SignUpActivity, "실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }


}