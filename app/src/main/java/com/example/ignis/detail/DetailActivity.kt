package com.example.ignis.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ignis.R
import com.example.ignis.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}