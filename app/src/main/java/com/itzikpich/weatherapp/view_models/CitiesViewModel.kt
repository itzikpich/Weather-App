package com.itzikpich.weatherapp.view_models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itzikpich.weatherapp.data.Repository
import com.itzikpich.weatherapp.models.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SharedViewModel"

class CitiesViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var isLoading = MutableLiveData(false)
    var citiesToShow = MutableLiveData<List<CityWeatherResult>>(mutableListOf())
    var allCitiesFetched = listOf<CityWeatherResult>()

    var isDataRestored = false

    fun getLocationsByLatLon(latLon: LatLon) {
        viewModelScope.launch {
            repository.getCloseLocationsByLatLon(latLon).collect { result ->
                when (result) {
                    is Result.Success -> {
                        isLoading.value = false
                        result.data?.list?.apply {
                            isDataRestored = true
                            allCitiesFetched = this
                            citiesToShow.value = this
                        }
                    }
                    is Result.Error -> {
                        Log.d(TAG, "error fetching data")
                        isLoading.value = false
                    }
                    is Result.Loading -> {
                        isLoading.value = true
                    }
                }
            }
        }
    }

    fun removeCityFromList(city: CityWeatherResult) {
        citiesToShow.value?.let { list ->
            val tempList : MutableList<CityWeatherResult> = list.toMutableList()
            tempList.remove(city)
            citiesToShow.value = tempList
        }
    }

}