package com.itzikpich.weatherapp.models


import com.google.gson.annotations.SerializedName

data class CityFindData(
    @SerializedName("message")
    val message: String?, // accurate
    @SerializedName("cod")
    val cod: String?, // 200
    @SerializedName("count")
    val count: Int?, // 5
    @SerializedName("list")
    val list: List<CityWeatherResult>?
)