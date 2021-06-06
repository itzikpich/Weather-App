package com.itzikpich.weatherapp.data

import com.itzikpich.weatherapp.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val apiService: RetrofitService) : RemoteDataSource {

    override suspend fun getCityWeatherResult(cityName: String): Flow<Result<CityWeatherResult>> {
        return flow {
            try {
                val response = apiService.getCityWeatherData(city = cityName)
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let {
                        emit(Result.Success(it))
                    }
                } else emit(Result.Error(Exception("response not succesful")))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(e))
            }
        }
    }

    override suspend fun getCityWeatherResultByLatLon(latLon: LatLon): Flow<Result<CityWeatherResult>> {
        return flow {
            try {
                val response = apiService.getCityWeatherDataByLatLon(lat = latLon.lat, lon = latLon.lon)
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let {
                        emit(Result.Success(it))
                    }
                } else emit(Result.Error(Exception("response not succesful")))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(e))
            }
        }
    }

    override suspend fun getCityFiveDaysResult(cityName: String): Flow<Result<FiveDaysWeatherData>> {
        return flow {
            try {
                val response = apiService.getFiveDaysWeatherData(city = cityName)
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let {
                        emit(Result.Success(it))
                    }
                } else emit(Result.Error(Exception("response not succesful")))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(e))
            }
        }
    }

    override suspend fun getCityFiveDaysResultByLatLon(latLon: LatLon): Flow<Result<FiveDaysWeatherData>> {
        return flow {
            try {
                val response = apiService.getFiveDaysWeatherDataByLatLon(lat = latLon.lat, lon = latLon.lon)
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let {
                        emit(Result.Success(it))
                    }
                } else emit(Result.Error(Exception("response not succesful")))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(e))
            }
        }
    }

    override suspend fun getCloseLocationsByLatLon(latLon: LatLon): Flow<Result<CityFindData>> {
        return flow {
            try {
                emit(Result.Loading)
                val response = apiService.getCloseLocationsByLatLon(lat = latLon.lat, lon = latLon.lon)
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let {
                        emit(Result.Success(it))
                    }
                } else emit(Result.Error(Exception("response not succesful")))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(e))
            }
        }
    }
}