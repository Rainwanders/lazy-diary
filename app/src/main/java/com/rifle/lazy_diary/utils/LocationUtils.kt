package com.rifle.lazy_diary.utils

import android.content.Context
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption

class LocationUtils {
    companion object {
        fun getCurrentLocation(context: Context, simpleCallback: SimpleCallback) {
            val mLocationClient = AMapLocationClient(context)
            val mLocationOption = AMapLocationClientOption()
            mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            mLocationOption.isOnceLocation = true
            mLocationOption.httpTimeOut = 20000
            mLocationClient.setLocationOption(mLocationOption)
            mLocationClient.startLocation()
            mLocationClient.setLocationListener { p0 ->
                p0?.let { simpleCallback.callback(it.city) }
            }
        }
    }
}