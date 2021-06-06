package com.itzikpich.weatherapp.models


import com.google.gson.annotations.SerializedName

data class FiveDaysWeatherData(
    @SerializedName("cod")
    val cod: String?, // 200
    @SerializedName("message")
    val message: Int?, // 0
    @SerializedName("cnt")
    val cnt: Int?, // 40
    @SerializedName("list")
    val list: List<Data>?,
    @SerializedName("city")
    val city: City?
) : CityData {

    data class Data(
        @SerializedName("dt")
        val dt: Long?, // 1622538000
        @SerializedName("main")
        val main: Main?,
        @SerializedName("weather")
        val weather: List<Weather?>?,
        @SerializedName("clouds")
        val clouds: Clouds?,
        @SerializedName("wind")
        val wind: Wind?,
        @SerializedName("visibility")
        val visibility: Int?, // 10000
        @SerializedName("pop")
        val pop: Double?, // 0
        @SerializedName("sys")
        val sys: Sys?,
        @SerializedName("dt_txt")
        val dtTxt: String? // 2021-06-01 09:00:00
    ) {
        data class Main(
            @SerializedName("temp")
            val temp: Double?, // 297.82
            @SerializedName("feels_like")
            val feelsLike: Double?, // 298.04
            @SerializedName("temp_min")
            val tempMin: Double?, // 297.69
            @SerializedName("temp_max")
            val tempMax: Double?, // 297.82
            @SerializedName("pressure")
            val pressure: Int?, // 1011
            @SerializedName("sea_level")
            val seaLevel: Int?, // 1011
            @SerializedName("grnd_level")
            val grndLevel: Int?, // 1003
            @SerializedName("humidity")
            val humidity: Int?, // 65
            @SerializedName("temp_kf")
            val tempKf: Double? // 0.13
        )

        data class Weather(
            @SerializedName("id")
            val id: Int?, // 801
            @SerializedName("main")
            val main: String?, // Clouds
            @SerializedName("description")
            val description: String?, // few clouds
            @SerializedName("icon")
            val icon: String? // 02d
        )

        data class Clouds(
            @SerializedName("all")
            val all: Int? // 13
        )

        data class Wind(
            @SerializedName("speed")
            val speed: Double?, // 3.83
            @SerializedName("deg")
            val deg: Int?, // 280
            @SerializedName("gust")
            val gust: Double? // 3.4
        )

        data class Sys(
            @SerializedName("pod")
            val pod: String? // d
        )
    }

    data class City(
        @SerializedName("id")
        val id: Int?, // 294801
        @SerializedName("name")
        val name: String?, // Haifa
        @SerializedName("coord")
        val coord: Coord?,
        @SerializedName("country")
        val country: String?, // IL
        @SerializedName("population")
        val population: Int?, // 267300
        @SerializedName("timezone")
        val timezone: Int?, // 10800
        @SerializedName("sunrise")
        val sunrise: Int?, // 1622514780
        @SerializedName("sunset")
        val sunset: Int? // 1622565786
    ) {
        data class Coord(
            @SerializedName("lat")
            val lat: Double?, // 32.8156
            @SerializedName("lon")
            val lon: Double? // 34.9892
        )
    }
}