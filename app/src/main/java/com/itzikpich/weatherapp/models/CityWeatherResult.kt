package com.itzikpich.weatherapp.models


import com.google.gson.annotations.SerializedName

data class CityWeatherResult(
    @SerializedName("coord")
    val coord: Coord?,
    @SerializedName("weather")
    val weather: List<Weather?>?,
    @SerializedName("base")
    val base: String?, // stations
    @SerializedName("main")
    val main: Main?,
    @SerializedName("visibility")
    val visibility: Int?, // 10000
    @SerializedName("wind")
    val wind: Wind?,
    @SerializedName("clouds")
    val clouds: Clouds?,
    @SerializedName("dt")
    val dt: Long?, // 1622527071
    @SerializedName("sys")
    val sys: Sys?,
    @SerializedName("timezone")
    val timezone: Int?, // 10800
    @SerializedName("id")
    val id: Int?, // 294801
    @SerializedName("name")
    val name: String?, // Haifa
    @SerializedName("cod")
    val cod: Int? // 200
) {
    data class Coord(
        @SerializedName("lon")
        val lon: Double?, // 34.9892
        @SerializedName("lat")
        val lat: Double? // 32.8156
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

    data class Main(
        @SerializedName("temp")
        val temp: Double?, // 297.68
        @SerializedName("feels_like")
        val feelsLike: Double?, // 297.99
        @SerializedName("temp_min")
        val tempMin: Double?, // 297.38
        @SerializedName("temp_max")
        val tempMax: Double?, // 297.71
        @SerializedName("pressure")
        val pressure: Int?, // 1011
        @SerializedName("humidity")
        val humidity: Int? // 69
    )

    data class Wind(
        @SerializedName("speed")
        val speed: Double?, // 1.54
        @SerializedName("deg")
        val deg: Int? // 0
    )

    data class Clouds(
        @SerializedName("all")
        val all: Int? // 20
    )

    data class Sys(
        @SerializedName("type")
        val type: Int?, // 1
        @SerializedName("id")
        val id: Int?, // 6853
        @SerializedName("country")
        val country: String?, // IL
        @SerializedName("sunrise")
        val sunrise: Long?, // 1622514780
        @SerializedName("sunset")
        val sunset: Long? // 1622565786
    )
}