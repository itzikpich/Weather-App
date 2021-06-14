package com.itzikpich.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.itzikpich.weatherapp.adapters.CityListAdapter
import com.itzikpich.weatherapp.databinding.FragmentCitiesBinding
import com.itzikpich.weatherapp.models.CityWeatherResult
import com.itzikpich.weatherapp.view_models.CitiesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


@AndroidEntryPoint
class CitiesFragment : BaseFragment<FragmentCitiesBinding>(FragmentCitiesBinding::inflate) {

    private val citiesViewModel by viewModels<CitiesViewModel>()

    private var job: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val adapter = CityListAdapter(
            celsius = sharedViewModel.isCelsius.value!!,
            onClick = { city ->
                onClick(city)
            },
            onLongClick = { city ->
                onLongClick(city)
            }
        )

        binding.citiesRecyclerView.adapter = adapter

        sharedViewModel.toolbarTitle.value = "Current Cities"

        sharedViewModel.lastLatLonLiveData.value?.let {
            if (citiesViewModel.allCitiesFetched.isEmpty()) {
                citiesViewModel.getLocationsByLatLon(it)
            }
        }

        sharedViewModel.isCelsius.observe(viewLifecycleOwner) { isCelsius ->
            adapter.celsius = isCelsius
            adapter.notifyDataSetChanged()
        }

        sharedViewModel.actionRestoreClicked.observe(viewLifecycleOwner) {
            if (it) {
                sharedViewModel.lastLatLonLiveData.value?.let {
                    citiesViewModel.getLocationsByLatLon(it)
                }
                sharedViewModel.actionRestoreClicked.value = false
            }
        }

        sharedViewModel.actionRefreshClicked.observe(viewLifecycleOwner) {
            if (it) {
                citiesViewModel.citiesToShow.value = citiesViewModel.allCitiesFetched
                sharedViewModel.actionRefreshClicked.value = false
            }
        }

        citiesViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.citiesLoader.isVisible = it
        }

        citiesViewModel.citiesToShow.observe(viewLifecycleOwner) {
            Log.d(TAG, "cities: $it")
            if (citiesViewModel.isDataRestored) {
                adapter.submitList(null)
                citiesViewModel.isDataRestored = false
            }
            adapter.submitList(it)
        }

        binding.citiesEditText.doOnTextChanged { text, start, before, count ->
            job?.cancel()
            if(text.isNullOrBlank()) return@doOnTextChanged
            job = lifecycleScope.launch {
                delay(500)
                val tempList : MutableList<CityWeatherResult> = mutableListOf()
                citiesViewModel.citiesToShow.value?.forEach {
                    tempList.add(it)
                }
                val filteredList = tempList.filter { it.name?.startsWith(text, true) == true }
                adapter.submitList(filteredList)
            }
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_list).isVisible = false
        menu.findItem(R.id.action_restore).isVisible = true
    }

    private fun onLongClick(city: CityWeatherResult) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(binding.root.context)
        builder.setMessage("Remove ${city.name} from list?")
        builder.setCancelable(true)
        builder.setPositiveButton("Delete") { dialog, id ->
            citiesViewModel.removeCityFromList(city)
            dialog.cancel()
        }
        builder.setNegativeButton("Cancel") { dialog, id ->
            dialog.cancel()
        }
        builder.create().show()
    }

    private fun onClick(city: CityWeatherResult) {
        sharedViewModel.currentCity.value = city
        findNavController().navigate(R.id.action_CitiesFragment_to_CityDetailsFragment)
    }

}