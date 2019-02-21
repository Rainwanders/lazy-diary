package com.rifle.simple_diary.ui.base

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import com.rifle.simple_diary.app.InterfaceCallback
import com.rifle.simple_diary.model.WeatherBean

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(), InterfaceCallback {

    override fun sucGetWeather(weatherBean: WeatherBean) { }

    override fun failGetWeather() { }

    override fun failed() { }

}