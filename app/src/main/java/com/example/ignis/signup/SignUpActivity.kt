package com.example.ignis.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ignis.R
import com.example.ignis.databinding.ActivitySignUpBinding
import com.example.ignis.network.AllApi
import retrofit2.Retrofit

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var retrofit: Retrofit
    private lateinit var apiService: AllApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

        }
    }
}