package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.myapplication.geolocation.GeoLocationManager
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.yandex.mapkit.location.Location

class GPSActivity : AppCompatActivity() {
    // Создание вспомогательных переменных
    private lateinit var locationManager: GeoLocationManager
    private var locationTrackingRequested = false
    private val LOCATION_PERMISSION_CODE = 1000
    private lateinit var latitudeTextView: TextView
    private lateinit var longitudeTextView: TextView
    private lateinit var statusTextView: TextView
    lateinit var location: Location
    private lateinit var onSuccess: (location: Location) -> Unit

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpsactivity)
//        Инициализация компонентов
        locationManager = GeoLocationManager(this)
        latitudeTextView = findViewById(R.id.textview_latitude)
        longitudeTextView = findViewById(R.id.textview_longitude)
        statusTextView = findViewById(R.id.textview_status)
// обработка нажатия на кнопку "Начать отслеживание"
        findViewById<Button>(R.id.button_start_location_scan).setOnClickListener {
            val permissionGranted = requestLocationPermission();
            if (permissionGranted) {
                locationManager.startLocationTracking(locationCallback)
                locationTrackingRequested = true
                statusTextView.text = "Started"
            }
        }
// Обработка нажатия на кнопку "Остановить отслеживание"
        findViewById<Button>(R.id.button_stop_location_scan).setOnClickListener {
            locationManager.stopLocationTracking()
            locationTrackingRequested = false
            statusTextView.text = "Stopped"
        }
//        Работа с textview
        longitudeTextView.getWidthHeight().apply {
            latitudeTextView.text = "$first"
            longitudeTextView.text = "$second"
        }
    }
// Функция получения данных для вывода данных
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
//           locationResult ?: return
            for (location in locationResult.locations) {
                latitudeTextView.text = location.latitude.toString()
                longitudeTextView.text = location.longitude.toString()
            }

        }
    }
// Функция преобразованя данных для вывода на экран
    fun View.getWidthHeight(): Pair<Float, Float> {
        measure(0, 0)
        val wight = measuredWidth.toFloat()
        val height = measuredHeight.toFloat()
        return Pair(wight, height)
    }

    private fun requestLocationPermission(): Boolean {
        var permissionGranted = false

// Если система ОС Marshmallow (8) или выше, нам нужно запросить разрешение во время выполнения
        val cameraPermissionNotGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_DENIED
        if (cameraPermissionNotGranted) {
            val permission = arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )

// Диалоговое окно разрешения отображения
            requestPermissions(permission, LOCATION_PERMISSION_CODE)
        } else {
// Разрешение уже предоставлено
            permissionGranted = true
        }
        return permissionGranted
    }

    @SuppressLint("SetTextI18n")
    @Suppress("DEPRECATED_IDENTITY_EQUALS")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode === LOCATION_PERMISSION_CODE) {
            if (grantResults.size === 2 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
// Разрешение было предоставлено
                locationManager.startLocationTracking(locationCallback)
                statusTextView.text = "Started"
            } else {
// В разрешении было отказано
                showAlert("Location permission was denied. Unable to track location.")
            }
        }
    }
// Функция "Паузы" отслеживания
    @SuppressLint("SetTextI18n")
    override fun onPause() {
        super.onPause()
        locationManager.stopLocationTracking()
        statusTextView.text = "Stopped"
    }
// Функция возобновления отслеживания
    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()

        if (locationTrackingRequested) {
            locationManager.startLocationTracking(locationCallback)
            statusTextView.text = "Started"
        }
    }
// Функция вывода окна с запросом на разрешение GPS
    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        val dialog = builder.create()
        dialog.show()
    }
}