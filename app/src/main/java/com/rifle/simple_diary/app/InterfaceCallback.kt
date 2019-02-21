package com.rifle.simple_diary.app

import com.rifle.simple_diary.model.WeatherBean

interface InterfaceCallback {

    //获取天气的回调
    fun sucGetWeather(weatherBean: WeatherBean)
    fun failGetWeather()

    fun failed()
}