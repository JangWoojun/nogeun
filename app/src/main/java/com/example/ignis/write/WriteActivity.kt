package com.example.ignis.write

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.ignis.R
import com.example.ignis.databinding.ActivityWriteBinding
import com.example.ignis.main.MainActivity
import com.example.ignis.main.MyPreferences
import com.example.ignis.main.WriteRequest
import com.example.ignis.network.AllApi
import com.example.ignis.network.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class WriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteBinding
    private var isImageUriString: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            etWrite.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                    // 텍스트 변경 전에 호출
                }

                override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                    if (etWrite.text.isNotEmpty()) {
                        val newBackgroundColor = ContextCompat.getColor(this@WriteActivity,
                            R.color.primary
                        )
                        btnWrite.backgroundTintList = ColorStateList.valueOf(newBackgroundColor)
                    } else {
                        val newBackgroundColor = ContextCompat.getColor(this@WriteActivity,
                            R.color.gray500
                        )
                        btnWrite.backgroundTintList = ColorStateList.valueOf(newBackgroundColor)
                    }
                }

                override fun afterTextChanged(editable: Editable?) {
                }
            })

            ivWriteBack.setOnClickListener { finish() }
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

            Log.d("TEST","a$isImageUriString")

            btnWrite.setOnClickListener {
                if (etWrite.text.isNotEmpty()) {
                    val sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
                    val token = sharedPreferences.getString("access_token", "")
                    val retrofitAPI = RetrofitClient.getInstance().create(AllApi::class.java)

                    val file: File = if (isImageUriString) {
                        File(absolutelyPath(Uri.parse(imageUriString)))
                    } else {
                        val tempFile = createTempImageFile()
                        imageUriString2!!.compress(Bitmap.CompressFormat.JPEG, 100, FileOutputStream(tempFile))
                        tempFile
                    }

                    if(!file.exists()) {
                        file.mkdirs()
                    }
                    val myPreferences = MyPreferences(this@WriteActivity)

                    val x = myPreferences.getDouble("x", 0.0)
                    val y = myPreferences.getDouble("y", 0.0)

                    val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(),file)
                    val body = MultipartBody.Part.createFormData("file",file.name,requestFile)
                    retrofitAPI.createdFeed(
                        authorization = "Bearer $token",
                        title = binding.etWrite.text.toString(),
                        file = body,
                        request = WriteRequest(
                            x = x,
                            y = y,
                        ),
                        ).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            when(response.code()) {
                                200 -> {
                                    val intent = Intent(this@WriteActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finishAffinity()
                                }
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(baseContext, "서버 실패", Toast.LENGTH_SHORT).show()
                            Log.d("TEST","q"+t)
                         }

                    })
                }
            }

        }

    }

    @SuppressLint("Recycle")
    fun absolutelyPath(path: Uri): String {

        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = contentResolver.query(path, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        val result = index?.let { c.getString(it) }

        return result!!
    }

    private fun createTempImageFile(): File {
        return File(externalCacheDir, "tempImageFile.jpg")
    }
}