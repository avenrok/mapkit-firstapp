package com.example.myapplication.geolocation

import android.content.Context
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class GeoLocationManager (context: Context) {
    // Создание вспомогательных перменных
    private val context: Context = context
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    var locationRequest= LocationRequest()
    private var startedLocationTracking = false
    // Вызов основных функций отслеживания
    init {
        configureLocationRequest()
        setupLocationProviderClient()
    }
    // Обращение к сервису определения местоположения
    private fun setupLocationProviderClient() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }
    //  Настройка запроса местоположения
    private fun configureLocationRequest() {
        locationRequest.interval = UPDATE_INTERVAL_MILLISECONDS
        locationRequest.fastestInterval = FASTEST_UPDATE_INTERVAL_MILLISECONDS
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
    // Начало отслеживания местоположения
    fun startLocationTracking(locationCallback: LocationCallback) {
        if (!startedLocationTracking) {
            //noinspection MissingPermission
            fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper())

            this.locationCallback = locationCallback

            startedLocationTracking = true
        }
    }
    // Остановка отслеживания местоположения
    fun stopLocationTracking() {
        if (startedLocationTracking) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }
    // Небольшие рассчёты для более удобной работы
    companion object {
        const val UPDATE_INTERVAL_MILLISECONDS: Long = 0
        const val FASTEST_UPDATE_INTERVAL_MILLISECONDS = UPDATE_INTERVAL_MILLISECONDS / 2
    }
}

