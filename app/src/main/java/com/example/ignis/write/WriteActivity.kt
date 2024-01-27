package com.example.ignis.write

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ignis.databinding.ActivityWriteBinding
import com.example.ignis.main.MainActivity
import com.example.ignis.network.AllApi
import com.example.ignis.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteBinding
    private var isImageUriString: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val imageUriString = intent.getStringExtra("imageUri")
            val imageUriString2 = intent.getParcelableExtra<Bitmap>("imageUri2")

            if (imageUriString != null) {
                Log.d("확인", imageUriString)
                val imageUri: Uri = Uri.parse(imageUriString)
                ivWrite.setImageURI(imageUri)
                isImageUriString = true
            }

            if (imageUriString2 != null) {
                Log.d("확인", imageUriString2.toString())
                ivWrite.setImageBitmap(imageUriString2)
                isImageUriString = false
            }


            btnWrite.setOnClickListener {
                if (etWrite.text.isNotEmpty()) {
                    val sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
                    val token = sharedPreferences.getString("access_token", "")
                    val retrofitAPI = RetrofitClient.getInstance().create(AllApi::class.java)

                }
            }

        }

    }
}