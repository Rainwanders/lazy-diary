package com.rifle.lazy_diary.app

import com.rifle.lazy_diary.model.WeatherBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterfaces {

    //获取天气
    @GET("/weather_mini")
    fun onGetWeather(@Query("city") city: String): Call<WeatherBean>
}