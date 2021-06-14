package com.itzikpich.weatherapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.itzikpich.weatherapp.adapters.HourListAdapter
import com.itzikpich.weatherapp.databinding.*
import com.itzikpich.weatherapp.models.*
import com.itzikpich.weatherapp.utilities.*
import com.itzikpich.weatherapp.view_holders.ViewBindingViewHolder


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity.mainActivitySybComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val hourItemDecoration = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL).apply {
            ContextCompat.getDrawable(binding.root.context, R.drawable.hour_divider)?.let { this.setDrawable(it) }
        }

        var listItems = listOf<CityData>()

        sharedViewModel.lastLatLonLiveData.observe(viewLifecycleOwner) { latLon ->
            latLon?.let {
                sharedViewModel.getAllDataByLatLon(latLon)
            }
        }

        sharedViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.homeLoader.isVisible = it
        }

        sharedViewModel.actionRefreshClicked.observe(viewLifecycleOwner) {
            if (it) {
                mainActivity.getLastLocation {
                    sharedViewModel.lastLatLonLiveData.value?.let { latLon ->
                        sharedViewModel.getAllDataByLatLon(latLon)
                    }
                }
                sharedViewModel.actionRefreshClicked.value = false
            }
        }

        sharedViewModel.cityData.observe(viewLifecycleOwner) {
            Log.d(TAG, "cityData: $it")
            listItems = it
            binding.homeRecyclerView.adapter?.notifyDataSetChanged()
        }

        sharedViewModel.isCelsius.observe(viewLifecycleOwner) { isCelsius ->
            mainActivity.sharedViewModel.toolbarTitle.value = if (isCelsius) "Celsius" else "Fahrenheit"
            binding.homeRecyclerView.adapter?.notifyDataSetChanged()
        }

        val adapter = object : RecyclerView.Adapter<ViewBindingViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindingViewHolder {
                return when(viewType) {
                    R.layout.list_item_main -> ViewBindingViewHolder.createVH(parent, ListItemMainBinding::inflate)
                    R.layout.list_item_hourly -> ViewBindingViewHolder.createVH(parent, ListItemHourlyBinding::inflate)
                    R.layout.list_item_sunrize_sunset -> ViewBindingViewHolder.createVH(parent, ListItemSunrizeSunsetBinding::inflate)
                    R.layout.list_item_current_details -> ViewBindingViewHolder.createVH(parent, ListItemCurrentDetailsBinding::inflate)
                    else -> throw Exception("viewType was not found")
                }
            }

            override fun onBindViewHolder(holder: ViewBindingViewHolder, position: Int) {
                if (listItems.isEmpty() || sharedViewModel.isCelsius.value == null) return
                val item = listItems[position]
                val celsius = sharedViewModel.isCelsius.value
                when (item) {
                    is CityMainData  -> {
                        (holder.binding as ListItemMainBinding).apply {
                            mainCurrentTime.text = item.time.toCustomMainDate()
                            item.currentTemp?.let { mainCurrentTemp.text = "${if(celsius!!) it.toInt() else it.toFahrenheit()}°" }
                            item.location.let { mainLocation.text = "$it" }
                            item.weatherIcon?.let { mainWeatherIcon.loadFromUrlToGlide("https://openweathermap.org/img/w/$it.png") }
                            item.maxTemp?.let { max ->
                                item.minTemp?.let { min ->
                                    mainMinMaxTemp.text = "Min ${if(celsius!!) min.toInt() else min.toFahrenheit()}° - Max ${if(celsius!!) max.toInt() else max.toFahrenheit()}°"
                                }
                            }
                        }
                    }
                    is FiveDaysWeatherData -> {
                        (holder.binding as ListItemHourlyBinding).let { binding ->
                            HourListAdapter(celsius!!).let { adapter ->
                                binding.hourlyRecyclerView.adapter = adapter
                                binding.hourlyRecyclerView.removeItemDecoration(hourItemDecoration)
                                binding.hourlyRecyclerView.addItemDecoration(hourItemDecoration)
                                adapter.submitList(item.list)
                            }
                        }
                    }
                    is CitySunrizeSunsetData -> {
                        (holder.binding as ListItemSunrizeSunsetBinding).apply {
                            item.sunrizeTime?.let { sunrize.text = "sunrize: ${it.toHMTime()}" }
                            item.sunsetTime?.let { sunset.text = "sunset: ${it.toHMTime()}" }
                            item.lengthOfDay?.let { lengthOfDay.text = "Length of day: ${it.toHMTime()} Hours" }
                            val progress = (1  - getTimeUntilMidnight() / 86400000) * 0.75f + 0.125f
                            weatherGraph.setProgress(progress)
                        }
                    }
                    is CityCurrentDetailsData -> {
                        (holder.binding as ListItemCurrentDetailsBinding).apply {
                            item.cloudiness?.let { currentDetailsCloudiness.text = "Cloudiness: $it" }
                            item.humidity?.let { currentDetailsHumidity.text = "Humidity: $it%" }
                            item.pressure?.let { currentDetailsPressure.text = "Pressure: $it hpa" }
                            item.visibility?.let { currentDetailsVisibility.text = "Visibility: $it km" }
                            item.wind?.let { currentDetailsWind.text = "Wind: $it km\\h" }
                        }
                    }
                }
            }

            override fun getItemViewType(position: Int): Int = when(position) {
                0 -> R.layout.list_item_main
                1 -> R.layout.list_item_hourly
                2 -> R.layout.list_item_sunrize_sunset
                3 -> R.layout.list_item_current_details
                else -> throw Exception("position must be between 0 and 3")
            }

            override fun getItemCount(): Int = sharedViewModel.cityData.value?.size ?: 0
        }

        binding.homeRecyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                ContextCompat.getDrawable(context, R.drawable.home_divider)?.let { this.setDrawable(it) }
            })
            this.adapter = adapter
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_list).isVisible = true
        menu.findItem(R.id.action_restore).isVisible = false
    }

}