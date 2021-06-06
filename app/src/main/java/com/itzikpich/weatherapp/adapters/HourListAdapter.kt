package com.itzikpich.weatherapp.adapters

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.itzikpich.weatherapp.databinding.ListItemHourBinding
import com.itzikpich.weatherapp.models.CityWeatherResult
import com.itzikpich.weatherapp.models.FiveDaysWeatherData
import com.itzikpich.weatherapp.utilities.loadFromUrlToGlide
import com.itzikpich.weatherapp.utilities.toFahrenheit
import com.itzikpich.weatherapp.utilities.toHMTime
import com.itzikpich.weatherapp.utilities.toHourDayTime
import com.itzikpich.weatherapp.view_holders.ViewBindingViewHolder

class HourListAdapter(var celsius: Boolean) : androidx.recyclerview.widget.ListAdapter<FiveDaysWeatherData.Data, ViewBindingViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindingViewHolder =
        ViewBindingViewHolder.createVH(parent, ListItemHourBinding::inflate)

    override fun onBindViewHolder(holder: ViewBindingViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            (holder.binding as ListItemHourBinding).apply {
                data.main?.humidity?.let { hourHumidity.text = "$it%" }
                data.weather?.firstOrNull()?.apply {
                    hourStatus.loadFromUrlToGlide("https://openweathermap.org/img/w/${this.icon}.png")
                }
                data.main?.temp?.let {
                    hourTemp.text = "${if (celsius) it.toInt() else it.toFahrenheit()}°"
                }
                data.dt?.let { hourTimeOfDay.text = it.toHourDayTime() }
            }
        }
        else Log.e("item is null", "")
    }

    class DiffCallback  : DiffUtil.ItemCallback<FiveDaysWeatherData.Data>() {
        override fun areItemsTheSame(oldItem: FiveDaysWeatherData.Data, newItem: FiveDaysWeatherData.Data): Boolean {
            return oldItem.dt == newItem.dt
        }

        override fun areContentsTheSame(oldItem: FiveDaysWeatherData.Data, newItem: FiveDaysWeatherData.Data): Boolean {
            return oldItem == newItem
        }

    }
}