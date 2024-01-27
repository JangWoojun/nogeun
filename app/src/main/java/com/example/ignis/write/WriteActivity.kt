package com.example.ignis.write

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.net.toUri
import com.example.ignis.R
import com.example.ignis.databinding.ActivityWriteBinding

class WriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val imageUriString = intent.getStringExtra("imageUri")

            if (imageUriString != null) {
                val imageUri: Uri = Uri.parse(imageUriString)
                ivWrite.setImageURI(imageUri)
            }
        }
    }
}