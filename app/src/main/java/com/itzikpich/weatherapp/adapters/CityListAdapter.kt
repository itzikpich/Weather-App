package com.itzikpich.weatherapp.adapters

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.itzikpich.weatherapp.databinding.ListItemCityBinding
import com.itzikpich.weatherapp.models.CityWeatherResult
import com.itzikpich.weatherapp.utilities.loadFromUrlToGlide
import com.itzikpich.weatherapp.utilities.toFahrenheit
import com.itzikpich.weatherapp.view_holders.ViewBindingViewHolder

class CityListAdapter(var celsius: Boolean, val onClick : (city: CityWeatherResult) -> Unit, val onLongClick : (city: CityWeatherResult) -> Unit) : androidx.recyclerview.widget.ListAdapter<CityWeatherResult, ViewBindingViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindingViewHolder =
        ViewBindingViewHolder.createVH(parent, ListItemCityBinding::inflate)

    override fun onBindViewHolder(holder: ViewBindingViewHolder, position: Int) {
        val city = getItem(position)
        if (city != null) {
            (holder.binding as ListItemCityBinding).apply {
                cityName.text = city.name
                city.weather?.firstOrNull()?.apply {
                    citySkyCondition.text = this.description
                    cityImageView.loadFromUrlToGlide("https://openweathermap.org/img/w/${this.icon}.png")
                }
                city.main?.temp?.let {
                    cityTemp.text = "${if (celsius!!) it.toInt() else it.toFahrenheit()}°"
                }
                city.main?.tempMax?.let { max ->
                    city.main.tempMin?.let { min ->
                        cityMixMax.text = "Min ${if(celsius) min.toInt() else min.toFahrenheit()}° - Max ${if(celsius) max.toInt() else max.toFahrenheit()}°"
                    }
                }
                holder.itemView.setOnClickListener {
                    onClick(city)
                }
                holder.itemView.setOnLongClickListener {
                    onLongClick(city)
                    true
                }
            }
        }
        else Log.e("item is null", "")
    }

    class DiffCallback  : DiffUtil.ItemCallback<CityWeatherResult>() {
        override fun areItemsTheSame(oldItem: CityWeatherResult, newItem: CityWeatherResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CityWeatherResult, newItem: CityWeatherResult): Boolean {
            return oldItem == newItem
        }

    }
}