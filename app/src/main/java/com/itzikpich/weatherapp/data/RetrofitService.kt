package com.itzikpich.weatherapp.data

import com.itzikpich.weatherapp.models.CityFindData
import com.itzikpich.weatherapp.models.CityWeatherResult
import com.itzikpich.weatherapp.models.FiveDaysWeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.itzikpich.weatherapp.utilities.API_KEY
import com.itzikpich.weatherapp.utilities.ITEMS_TO_FIND
import com.itzikpich.weatherapp.utilities.UNITS


interface RetrofitService {

    //api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
    @GET("data/2.5/weather")
    suspend fun getCityWeatherData(
        @Query("q") city: String,
        @Query("appId") apiKey: String = API_KEY,
        @Query("units") unit: String = UNITS
    ) : Response<CityWeatherResult>

    // api.openweathermap.org/data/2.5/weather?units=metric&lat=32.764565&lon=34.967715&appid=c900ebc84b3552b111e78ec9e5bdfb49
    @GET("data/2.5/weather")
    suspend fun getCityWeatherDataByLatLon(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appId") apiKey: String = API_KEY,
        @Query("units") unit: String = UNITS
    ) : Response<CityWeatherResult>

    //api.openweathermap.org/data/2.5/forecast?q={city name}&appid={API key}
    @GET("data/2.5/forecast")
    suspend fun getFiveDaysWeatherData(
        @Query("q") city: String,
        @Query("appId") apiKey: String = API_KEY,
        @Query("units") unit: String = UNITS
    ) : Response<FiveDaysWeatherData>

    // api.openweathermap.org/data/2.5/forecast?lat=32.764565&lon=34.967715&units=metric&appid={API key}
    @GET("data/2.5/forecast")
    suspend fun getFiveDaysWeatherDataByLatLon(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appId") apiKey: String = API_KEY,
        @Query("units") unit: String = UNITS
    ) : Response<FiveDaysWeatherData>

    // aapi.openweathermap.org/data/2.5/find?lat=32.764565&lon=34.967715&units=metric&appid={API key}&cnt=50
    @GET("data/2.5/find")
    suspend fun getCloseLocationsByLatLon(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("cnt") cnt: Int = ITEMS_TO_FIND,
        @Query("appId") apiKey: String = API_KEY,
        @Query("units") unit: String = UNITS
    ) : Response<CityFindData>


}