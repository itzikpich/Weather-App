package com.itzikpich.weatherapp.data

import com.itzikpich.weatherapp.models.*
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    suspend fun getCityWeatherResult(cityName: String): Flow<Result<CityWeatherResult>>
    suspend fun getCityWeatherResultByLatLon(latLon: LatLon): Flow<Result<CityWeatherResult>>
    suspend fun getCityFiveDaysResult(cityName: String): Flow<Result<FiveDaysWeatherData>>
    suspend fun getCityFiveDaysResultByLatLon(latLon: LatLon): Flow<Result<FiveDaysWeatherData>>
    suspend fun getCloseLocationsByLatLon(latLon: LatLon): Flow<Result<CityFindData>>

}