package com.itzikpich.weatherapp.view_models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.itzikpich.weatherapp.data.Repository
import com.itzikpich.weatherapp.models.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SharedViewModel"

class SharedViewModel @Inject constructor(
    app:Application,
    private val repository: Repository
    ) : AndroidViewModel(app) {

    var actionRefreshClicked = MutableLiveData(false)
    var actionRestoreClicked = MutableLiveData(false)
    var actionListClicked = MutableLiveData(false)
    var actionToggleClicked = MutableLiveData(false)
    var cityData = MutableLiveData<List<CityData>>(mutableListOf())

    var isCelsius: MutableLiveData<Boolean> = MutableLiveData(true)
    var toolbarTitle = MutableLiveData("")

    var isLoading = MutableLiveData(false)

    var lastLatLonLiveData: MutableLiveData<LatLon> = MutableLiveData()

    var currentCity = MutableLiveData<CityWeatherResult>()
    var refreshedCurrentCity = MutableLiveData<CityWeatherResult?>()

    fun getAllData(cityName: String) {
        viewModelScope.launch {
            isLoading.value = true
            combine(repository.getCityWeatherResult(cityName), repository.getCityFiveDaysResult(cityName)) { city, five ->
                Log.d(TAG, "getAllData: $city, $five")
                val list = mutableListOf<CityData>()

                when(city) {
                    is Result.Success -> {
                        city.data?.apply {
                            list.add(this.toMainData())
                            list.add(this.toSunDetails())
                            list.add(this.toCurrentDetails())
                        }
                    }
                    is Result.Error -> Log.d(TAG, "error fetching data")
                    is Result.Loading -> {} // show loader
                }
                when(five) {
                    is Result.Success -> {
                        five.data?.apply {
                            list.add(if (list.isEmpty()) 0 else 1, this)
                        }
                    }
                    is Result.Error -> Log.d(TAG, "error fetching data")
                    is Result.Loading -> {} // show loader
                }
                cityData.value = list
                isLoading.value = false
            }.collect()
        }
    }
    fun getAllDataByLatLon(latLon: LatLon) {
        viewModelScope.launch {
            isLoading.value = true
            combine(repository.getCityWeatherResultByLatLon(latLon), repository.getCityFiveDaysResultByLatLon(latLon)) { city, five ->
                Log.d(TAG, "getAllData: $city, $five")
                val list = mutableListOf<CityData>()

                when(city) {
                    is Result.Success -> {
                        city.data?.apply {
                            list.add(this.toMainData())
                            list.add(this.toSunDetails())
                            list.add(this.toCurrentDetails())
                        }
                    }
                    is Result.Error -> Log.d(TAG, "error fetching data")
                    is Result.Loading -> {} // show loader
                }
                when(five) {
                    is Result.Success -> {
                        five.data?.apply {
                            list.add(if (list.isEmpty()) 0 else 1, this)
                        }
                    }
                    is Result.Error -> Log.d(TAG, "error fetching data")
                    is Result.Loading -> {} // show loader
                }
                cityData.value = list
                isLoading.value = false
            }.collect()
        }
    }

    fun getLocationByLatLon(latLon: LatLon) {
        viewModelScope.launch {
            isLoading.value = true
            repository.getCityWeatherResultByLatLon(latLon).collect { result ->
                when (result) {
                    is Result.Success -> {
                        result.data?.apply {
                            refreshedCurrentCity.value = this
                        }

                        isLoading.value = false
                    }
                    is Result.Error -> {
                        Log.d(TAG, "error fetching data")
                        isLoading.value = false
                    }
                    is Result.Loading -> {
                        isLoading.value = true
                    } // show loader
                }
            }
        }
    }

    fun toggleToolbar() {
        isCelsius.value = !isCelsius.value!!
    }

}