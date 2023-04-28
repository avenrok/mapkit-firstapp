package com.example.myapplication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider


class MainActivity : AppCompatActivity() {
    //  создаем переменную для работы с местоположением
//    private lateinit var fusedLocationClient: FusedLocationProviderClient
    //  Создаем переменную для работы с картой
    lateinit var mapview: MapView
//    Также переменные для работы с местоположением
//    private lateinit var locationRequest: LocationRequest
//    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//  Присваиваем ключ API
        MapKitFactory.setApiKey("5bec10c0-8d75-4cfc-8214-32baa1f3921e")
//  Инициализируем компоненты
        MapKitFactory.initialize(this)
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContentView(R.layout.activity_main)
//  Присваиваем нашей переменной ID карты, которую прописали в activity_main
        mapview = findViewById(R.id.mapViewR)
//        Приближаем камену к Политеху
        mapview.map.move(
            CameraPosition(Point(53.345161, 83.782327), 14.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 10f), null
        )
//        Добавление обозначнеия маркера
        val imageProvider = ImageProvider.fromResource(this, R.drawable.marker)
//        Добавление маркеров
        //    1. Александрийский маяк
        mapview.map.mapObjects.addPlacemark(Point(43.576134, 39.724229), imageProvider)
        //   2. Пирамида Хеопса
        mapview.map.mapObjects.addPlacemark(Point(29.978163, 31.135301), imageProvider)
        //   3. Древние руины Вивилона
        mapview.map.mapObjects.addPlacemark(Point(32.541771, 44.421930), imageProvider)
        //   4. Колосс Родосский
        mapview.map.mapObjects.addPlacemark(Point(36.451048, 28.226614), imageProvider)
        //   5. Храм Артемиды Эфесской
        mapview.map.mapObjects.addPlacemark(Point(37.948529, 27.363869), imageProvider)
        //   6. Мавзолей в Галикарнасе
        mapview.map.mapObjects.addPlacemark(Point(37.037957, 27.423809), imageProvider)
        //   7. Статуя Зевса в Олимпии
        mapview.map.mapObjects.addPlacemark(Point(37.637811, 21.630211), imageProvider)
        //   8. Алтайский государственный университет им. И.И. Ползунова
        mapview.map.mapObjects.addPlacemark(Point(53.345161, 83.782327), imageProvider)

    }

    // Функция завершения приложения
    override fun onStop() {
        mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    // Функция старта приложения
    override fun onStart() {
        mapview.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }


}