package com.itzikpich.weatherapp.models

data class CityMainData(
    val time: Long,
    val location: String?,
    val minTemp: Double?,
    val maxTemp: Double?,
    val currentTemp: Double?,
    val weatherIcon: String?
): CityData

fun CityWeatherResult.toMainData() = CityMainData(
    System.currentTimeMillis(), this.name, this.main?.tempMin, this.main?.tempMax, this.main?.temp, this.weather?.firstOrNull()?.icon
)

data class CitySunrizeSunsetData(
    val sunrizeTime: Long?,
    val sunsetTime: Long?,
    val lengthOfDay: Long?
): CityData


fun CityWeatherResult.toSunDetails() = CitySunrizeSunsetData(this.sys?.sunrise, this.sys?.sunset,
    (this.sys?.sunrise?.let { this.sys.sunset?.minus(it) }) ?: 0L)

data class CityCurrentDetailsData(
    val wind: Double?,
    val cloudiness: String?,
    val humidity: Int?,
    val pressure: Int?,
    val visibility: Int?
): CityData

fun CityWeatherResult.toCurrentDetails() = CityCurrentDetailsData(
    this.wind?.speed, this.weather?.firstOrNull()?.description, this.main?.humidity, this.main?.pressure, this.visibility
)

interface CityData