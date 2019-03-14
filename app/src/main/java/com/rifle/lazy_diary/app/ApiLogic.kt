package com.rifle.lazy_diary.app

import com.rifle.lazy_diary.BuildConfig
import com.rifle.lazy_diary.model.WeatherBean
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiLogic {
    companion object {
        fun getWeather(presenter: InterfaceCallback, city: String) {
            val callback = getService.onGetWeather(city)
            callback.enqueue(object : Callback<WeatherBean> {
                override fun onFailure(call: Call<WeatherBean>?, t: Throwable?) {
                    presenter.failed()
                }

                override fun onResponse(call: Call<WeatherBean>?, response: Response<WeatherBean>?) {
                    if (response?.isSuccessful == true) {
                        presenter.sucGetWeather(response.body())
                    }else {
                        presenter.failGetWeather()
                    }
                }
            })
        }


        private val getService = getRetrofit().create(ApiInterfaces::class.java)
        private var retrofit: Retrofit? = null
        private fun getRetrofit(): Retrofit {
            if (retrofit == null) {
                synchronized(this) {
                    if (retrofit == null) {
                        retrofit = Retrofit.Builder()
                                .baseUrl(Constants.baseUrl)
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                .client(createOkHttpClient())
                                .build()
                    }
                }
            }
            return retrofit!!
        }

        private fun createOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(
                            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                            else HttpLoggingInterceptor.Level.NONE))
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .writeTimeout(5000, TimeUnit.MILLISECONDS)
                    .build()
        }
    }
}