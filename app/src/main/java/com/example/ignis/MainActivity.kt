package com.example.ignis

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import com.google.android.gms.location.LocationRequest
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.ignis.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private lateinit var mLocationRequest: LocationRequest
    private val REQUEST_PERMISSION_LOCATION = 10
    private var kakaoMap: KakaoMap? = null
    private var myLocation: Location? = null
    companion object {
        const val TAG = "확인"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

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
                    }

                    override fun getZoomLevel(): Int {
                        return 15
                    }
                })

        }
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
            onLocationChanged(locationResult.lastLocation!!)
        }
    }

    fun onLocationChanged(location: Location) {
        val cameraUpdate = CameraUpdateFactory.newCenterPosition(LatLng.from(location.latitude, location.longitude))
        kakaoMap!!.moveCamera(cameraUpdate)

        myLocation = location

        val styles = kakaoMap!!.labelManager!!
            .addLabelStyles(LabelStyles.from(LabelStyle.from(R.drawable.my_location)))
        val options: LabelOptions =
            LabelOptions.from(LatLng.from(location.latitude, location.longitude)).setStyles(styles)
        val layer = kakaoMap!!.labelManager!!.layer
        val label = layer!!.addLabel(options)

        label.show()

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


}