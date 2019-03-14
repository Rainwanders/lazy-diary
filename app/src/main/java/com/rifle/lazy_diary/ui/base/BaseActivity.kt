package com.rifle.lazy_diary.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rifle.lazy_diary.app.InterfaceCallback
import com.rifle.lazy_diary.model.WeatherBean

@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity(), InterfaceCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayout())
        initView()
        setOnclick()
    }

    abstract fun setLayout(): Int

    abstract fun initView()

    abstract fun setOnclick()

    override fun sucGetWeather(weatherBean: WeatherBean) { }

    override fun failGetWeather() { }

    override fun failed() { }

}