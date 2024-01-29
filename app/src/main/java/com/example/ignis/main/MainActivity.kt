package com.example.ignis.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Location
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.ignis.R
import com.example.ignis.databinding.ActivityMainBinding
import com.example.ignis.detail.DetailActivity
import com.example.ignis.network.AllApi
import com.example.ignis.network.RetrofitClient
import com.example.ignis.profile.ProfileActivity
import com.example.ignis.result.ResultActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.kakao.sdk.user.UserApiClient
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.camera.CameraAnimation
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.camera.CameraUpdateFactory.zoomTo
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
<<<<<<< Updated upstream
import com.kakao.vectormap.route.RouteLineStyle
import com.kakao.vectormap.route.RouteLineStyles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
=======
import kotlinx.coroutines.Dispatchers
>>>>>>> Stashed changes
import kotlinx.coroutines.launch
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private lateinit var mLocationRequest: LocationRequest
    private var kakaoMap: KakaoMap? = null
    private var myLocation: Location? = null
    private var isFirst = true
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var recognizerIntent: Intent

    companion object {
        const val REQUEST_PERMISSION_LOCATION = 10
        const val RECORD_AUDIO_PERMISSION_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            checkPermission()

            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this@MainActivity)
            speechRecognizer.setRecognitionListener(recognitionListener)

            recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "검색하실 단어를 말하세요!")

            searchVoiceButton.setOnClickListener {
                Toast.makeText(this@MainActivity, "검색하실 단어를 말하세요!", Toast.LENGTH_LONG).show()
                speechRecognizer.startListening(recognizerIntent)
            }

            UserApiClient.instance.me { user, _ ->
                Glide
                    .with(baseContext)
                    .load(user?.kakaoAccount?.profile?.thumbnailImageUrl)
                    .into(myImage)
            }

            myImage.setOnClickListener {
                val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity(intent)
            }
            searchButton.setOnClickListener {
                val intent = Intent(this@MainActivity, ResultActivity::class.java)
                intent.putExtra("title", inputText.text.toString())
                startActivity(intent)
            }


            KeyboardVisibilityEvent.setEventListener(
                this@MainActivity
            ) { isOpen ->
                if (isOpen) {
                    inputText.hint = "검색어를 입력해주세요"
                    searchButton.visibility = View.VISIBLE
                    photoUploadButton.visibility = View.GONE
                    myLocationButton.visibility = View.GONE
                } else {
                    inputText.hint = "터치해서 동네 사진 검색하기"
                    searchButton.visibility = View.GONE
                    photoUploadButton.visibility = View.VISIBLE
                    myLocationButton.visibility = View.VISIBLE
                }
            }

            photoUploadButton.setOnClickListener {
                val bottomSheet = BottomSheet()
                bottomSheet.show(supportFragmentManager, bottomSheet.tag)
            }


            inputText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                    // 텍스트 변경 전에 호출
                }

                override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                    if (inputText.text.isNotEmpty()) {
                        val newBackgroundColor = ContextCompat.getColor(this@MainActivity,
                            R.color.primary
                        )

                        searchButton.backgroundTintList = ColorStateList.valueOf(newBackgroundColor)
                    } else {
                        val newBackgroundColor = ContextCompat.getColor(this@MainActivity,
                            R.color.gray500
                        )
                        searchButton.backgroundTintList = ColorStateList.valueOf(newBackgroundColor)
                    }
                }

                override fun afterTextChanged(editable: Editable?) {
                }
            })

            inputText.setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE || (event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                    val intent = Intent(this@MainActivity, ResultActivity::class.java)
                    intent.putExtra("title", inputText.text.toString())
                    startActivity(intent)
                    return@setOnEditorActionListener true // Consume the event
                }
                false // Let the system handle the event
            }

            mLocationRequest =  LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }

            mapView.start(object : MapLifeCycleCallback() {
                override fun onMapDestroy() {
                }

                override fun onMapError(error: Exception) {
                }
            },
                object : KakaoMapReadyCallback() {
                    override fun onMapReady(kakaoMap: KakaoMap) {
                        this@MainActivity.kakaoMap = kakaoMap
                        if (checkPermissionForLocation(this@MainActivity)) {
                            startLocationUpdates()
                        }

                        myLocationButton.setOnClickListener {
                            val cameraUpdate = CameraUpdateFactory.newCenterPosition(
                                LatLng.from(myLocation!!.latitude, myLocation!!.longitude))
                            kakaoMap.moveCamera(zoomTo(16))
                            kakaoMap.moveCamera(cameraUpdate, CameraAnimation.from(300, true, true))
                        }

                        kakaoMap.setOnLabelClickListener { kakaoMap, layer, label ->
                            val intent = Intent(this@MainActivity, DetailActivity::class.java)
                            intent.putExtra("Id", label.tag.toString().toLong())
                            startActivity(intent)
                        }

                    }

                    override fun getZoomLevel(): Int {
                        return 16
                    }
                })

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermission() {
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), RECORD_AUDIO_PERMISSION_CODE)
        }
    }

    private val recognitionListener = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {}

        override fun onBeginningOfSpeech() {}

        override fun onRmsChanged(rmsdB: Float) {}

        override fun onBufferReceived(buffer: ByteArray?) {}

        override fun onEndOfSpeech() {}

        override fun onError(error: Int) {}

        override fun onResults(results: Bundle?) {
            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            if (!matches.isNullOrEmpty()) {
                val result = matches[0]
                binding.inputText.setText(result)
            }
        }

        override fun onPartialResults(partialResults: Bundle?) {}

        override fun onEvent(eventType: Int, params: Bundle?) {}
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.destroy()
    }

    private fun startLocationUpdates() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            onLocationChanged(locationResult.lastLocation)
        }
    }

    fun onLocationChanged(location: Location?) {
        if (location != null) {
            val cameraUpdate = CameraUpdateFactory.newCenterPosition(LatLng.from(location.latitude, location.longitude))
            kakaoMap!!.moveCamera(cameraUpdate)

            myLocation = location

            val styles = kakaoMap?.labelManager?.addLabelStyles(LabelStyles.from(LabelStyle.from(R.drawable.my_location)))
            val options: LabelOptions =
                LabelOptions.from(LatLng.from(location.latitude, location.longitude)).setStyles(styles)
            val layer = kakaoMap!!.labelManager!!.layer
            val label = layer!!.addLabel(options)

            label.show()

            if (isFirst) {
                val sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
                val token = sharedPreferences.getString("access_token", "")

                val retrofitAPI = RetrofitClient.getInstance().create(AllApi::class.java)
                val call: Call<List<KmResponse>> = retrofitAPI.km("Bearer $token", myLocation!!.longitude, myLocation!!.latitude)
                call.enqueue(object : Callback<List<KmResponse>> {
                    override fun onResponse(call: Call<List<KmResponse>>, response: Response<List<KmResponse>>) {
                        Log.d("확인", response.body().toString())
<<<<<<< Updated upstream
                        response.body()!!.forEach {
                            GlobalScope.launch(Dispatchers.IO) {
                                val bitmap: Bitmap = Glide.with(this@MainActivity)
                                    .asBitmap()
                                    .override(160, 160)
                                    .load(it.image_url)
                                    .submit()
                                    .get()


                                val styles1 = kakaoMap!!.labelManager!!
                                    .addLabelStyles(LabelStyles.from(LabelStyle.from(bitmap).setZoomLevel(16)))
                                val options1: LabelOptions =
                                    LabelOptions.from(LatLng.from(it.y, it.x))
                                        .setStyles(styles1)

                                val layer1 = kakaoMap!!.labelManager!!.layer
                                val label1 = layer1!!.addLabel(options1)

                                label1.tag = it.id
                                CoroutineScope(Dispatchers.Main).launch {
                                    label1.show()
                                }
                                isFirst = false

                            }



=======

                        lifecycleScope.launch(Dispatchers.IO) {
                            response.body()!!.forEach {
                                val url = URL(it.image_url)
                                val connection = url.openConnection()
                                val inputStream = connection.getInputStream()
                                val image:Bitmap = BitmapFactory.decodeStream(inputStream)
                                Log.d("TEST","g$image")

                                val styles1 = kakaoMap!!.labelManager!!
                                    .addLabelStyles(LabelStyles.from(LabelStyle.from(image).setZoomLevel(16)))
                                val options1: LabelOptions =
                                    LabelOptions.from(LatLng.from(it.y, it.x))
                                        .setStyles(styles1)

                                val layer1 = kakaoMap!!.labelManager!!.layer
                                val label1 = layer1!!.addLabel(options1)

                                label1.tag = it.id
                                label1.show()

                                isFirst = false
                            }
>>>>>>> Stashed changes
                        }
                    }

                    override fun onFailure(call: Call<List<KmResponse>>, t: Throwable) {
                        Log.d("확인", t.toString())
                    }
                })
            }

            val myPreferences = MyPreferences(this)

            myPreferences.saveDouble("x", location.longitude)
            myPreferences.saveDouble("y", location.latitude)

        }


    }


    private fun checkPermissionForLocation(context: Context): Boolean {
        return if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_LOCATION)
            false
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
            } else {
                Toast.makeText(this, "권한이 없어 지도를 사용할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    class ImageDownloadTask(private val listener: OnImageDownloadListener) : AsyncTask<String, Void, Bitmap?>() {

        interface OnImageDownloadListener {
            fun onImageDownloaded(bitmap: Bitmap?)
        }

        override fun doInBackground(vararg params: String?): Bitmap? {
            val imageUrl = params[0]

            try {
                val url = URL(imageUrl)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()

                val input: InputStream = connection.inputStream
                return BitmapFactory.decodeStream(input)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            listener.onImageDownloaded(result)
        }
    }


}