package com.rifle.simple_diary.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rifle.simple_diary.app.InterfaceCallback
import com.rifle.simple_diary.model.WeatherBean

abstract class BaseFragment : Fragment(), InterfaceCallback {
    var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return if (setLayout() == 0) {
            super.onCreateView(inflater, container, savedInstanceState)
        } else {
            inflater.inflate(setLayout(), null)
        }

    }

    protected abstract fun setLayout(): Int

    override fun sucGetWeather(weatherBean: WeatherBean) {}

    override fun failGetWeather() {}

    override fun failed() {}
}