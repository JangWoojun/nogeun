package com.example.ignis.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.ignis.R
import com.example.ignis.write.WriteActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet(context: Context) : BottomSheetDialogFragment() {

    private val PICK_IMAGE_REQUEST = 1



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.bottom_sheet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<ConstraintLayout>(R.id.close_button)?.setOnClickListener {
            dismiss()
        }
        view?.findViewById<CardView>(R.id.gallery_button)?.setOnClickListener {
            openGallery()
        }
        view?.findViewById<CardView>(R.id.camera_button)?.setOnClickListener {
            checkCameraPermissionAndTakePicture()
        }
    }

    private fun checkCameraPermissionAndTakePicture() {
        if (ContextCompat.checkSelfPermission(context as Activity, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 권한이 없으면 권한 요청
            ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.CAMERA), 1)
        } else {
            // 권한이 있으면 사진 찍기
            takePicture()
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data

            val nextActivityIntent = Intent(context, WriteActivity::class.java)
            selectedImageUri?.let { uri ->
                nextActivityIntent.putExtra("imageUri", uri.toString())
                startActivity(nextActivityIntent)
            }
        }
    }

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val takenImage = result.data?.extras?.get("data")
            Log.d("확인", "Image URI: $takenImage")

            val nextActivityIntent = Intent(context, WriteActivity::class.java)
            nextActivityIntent.putExtra("imageUri", takenImage.toString())
            startActivity(nextActivityIntent)
        }
    }

    private fun takePicture() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureLauncher.launch(takePictureIntent)
    }
}