package com.rifle.lazy_diary.model

data class WeatherBean(
        var data: WeatherItemBean,
        var status: Int,
        var desc: String
) {
    data class WeatherItemBean(
            var city: String,
            var ganmao: String,
            var wendu: String,
            var yesterday: WeatherInfoBean,
            var forecast: List<WeatherInfoBean>
    ) {
        data class WeatherInfoBean(
                var date: String,
                var high: String,
                var fx: String,
                var low: String,
                var fl: String,
                var type: String
        )
    }
}