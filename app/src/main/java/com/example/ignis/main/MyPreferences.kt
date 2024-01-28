package com.example.ignis.main

import android.content.Context
import android.content.SharedPreferences

class MyPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)

    fun saveDouble(key: String, value: Double) {
        val editor = sharedPreferences.edit()
        editor.putFloat(key, value.toFloat()) // Double을 Float로 변환하여 저장
        editor.apply()
    }

    fun getDouble(key: String, defaultValue: Double): Double {
        // 저장된 값을 가져올 때 Float로 받은 후 Double로 변환하여 반환
        return sharedPreferences.getFloat(key, defaultValue.toFloat()).toDouble()
    }
}

