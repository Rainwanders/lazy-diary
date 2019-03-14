package com.rifle.lazy_diary.app

import com.rifle.lazy_diary.model.WeatherBean

interface InterfaceCallback {

    //获取天气的回调
    fun sucGetWeather(weatherBean: WeatherBean)
    fun failGetWeather()

    fun failed()
}