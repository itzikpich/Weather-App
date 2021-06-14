package com.itzikpich.weatherapp

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.core.view.isVisible
import com.itzikpich.weatherapp.databinding.FragmentCityDetailsBinding
import com.itzikpich.weatherapp.models.LatLon
import com.itzikpich.weatherapp.utilities.loadFromUrlToGlide
import com.itzikpich.weatherapp.utilities.toFahrenheit
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CityDetailsFragment: BaseFragment<FragmentCityDetailsBinding>(FragmentCityDetailsBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        sharedViewModel.actionRefreshClicked.observe(viewLifecycleOwner) { clicked ->
            if(clicked) {
                sharedViewModel.currentCity.value?.coord?.let { coord ->
                    coord.lat?.let { lat ->
                        coord.lon?.let { lon ->
                            sharedViewModel.getLocationByLatLon(LatLon(lat, lon))
                        }
                    }
                }
                sharedViewModel.actionRefreshClicked.value = false
            }
        }

        sharedViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.cityLoader.isVisible = it
        }

        sharedViewModel.refreshedCurrentCity.observe(viewLifecycleOwner) { city ->
            city?.let {
                sharedViewModel.currentCity.value = city
                sharedViewModel.isCelsius.value = sharedViewModel.isCelsius.value
                sharedViewModel.refreshedCurrentCity.value = null
            }
        }

        sharedViewModel.isCelsius.observe(viewLifecycleOwner) { isCelsius ->
            sharedViewModel.currentCity.value?.let { city ->
                sharedViewModel.toolbarTitle.value = city.name
                binding.apply {
                    cityName.text = city.name
                    city.weather?.firstOrNull()?.apply {
                        citySkyCondition.text = this.description
                        cityImageView.loadFromUrlToGlide("https://openweathermap.org/img/w/${this.icon}.png")
                    }
                    city.main?.temp?.let {
                        cityTemp.text = "${if (isCelsius) it.toInt() else it.toFahrenheit()}°"
                    }
                    city.main?.tempMax?.let { max ->
                        city.main.tempMin?.let { min ->
                            cityMixMax.text =
                                "Min ${if (isCelsius) min.toInt() else min.toFahrenheit()}° - Max ${if (isCelsius) max.toInt() else max.toFahrenheit()}°"
                        }
                    }
                }
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_list).isVisible = false
        menu.findItem(R.id.action_restore).isVisible = false
    }

}