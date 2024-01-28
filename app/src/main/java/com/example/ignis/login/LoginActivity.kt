package com.example.ignis.login

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ignis.main.MainActivity
import com.example.ignis.databinding.ActivityLoginBinding
import com.example.ignis.network.AllApi
import com.example.ignis.network.RetrofitClient
import com.example.ignis.signup.SignUpActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    companion object {
        const val TAG = "확인"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오계정으로 로그인 실패", error)
                } else if (token != null) {
                    Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                }
            }

            login.setOnClickListener {
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@LoginActivity)) {
                    UserApiClient.instance.loginWithKakaoTalk(this@LoginActivity) { token, error ->
                        if (error != null) {
                            Log.e(TAG, "카카오톡으로 로그인 실패", error)

                            // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                            // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                                return@loginWithKakaoTalk
                            }

                            // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                            UserApiClient.instance.loginWithKakaoAccount(this@LoginActivity, callback = callback)
                        } else if (token != null) {
                            Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                            Log.d("확인", "0")
                            UserApiClient.instance.me { user, _ ->
                                if (user != null) {
                                    val retrofitAPI = RetrofitClient.getInstance().create(AllApi::class.java)
                                    val call: Call<LoginResponse> = retrofitAPI.login(
                                        LoginRequest(
                                            user.kakaoAccount?.profile?.nickname.toString(),
                                            user.kakaoAccount?.email.toString(),
                                            user.kakaoAccount?.profile?.profileImageUrl.toString()
                                        )
                                    )

                                    call.enqueue(object : Callback<LoginResponse> {

                                        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                                            if (response.isSuccessful) {
                                                Log.d("확인", "1")
                                                val sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
                                                val editor = sharedPreferences.edit()
                                                editor.putString("access_token", response.body()!!.access_token)
                                                editor.apply()
                                                Log.d("TEST","token: ${response.body()!!.access_token}")
                                                if (response.body()?.is_signed_up == true) {
                                                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                                    finishAffinity()
                                                } else {
                                                    startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
                                                }
                                            } else {
                                                Log.d("확인", "2")
                                                Log.d("확인", response.toString())
                                                Toast.makeText(this@LoginActivity, "실패했습니다.", Toast.LENGTH_SHORT).show()
                                            }
                                        }

                                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                            Toast.makeText(this@LoginActivity, "실패했습니다.", Toast.LENGTH_SHORT).show()
                                        }
                                    })
                                }
                            }
                        }
                    }
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(this@LoginActivity, callback = callback)
                }
            }
        }
    }
}