package com.itzikpich.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.itzikpich.weatherapp.databinding.ActivityMainBinding
import com.itzikpich.weatherapp.view_models.SharedViewModel
import javax.inject.Inject
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.itzikpich.weatherapp.models.LatLon
import dagger.hilt.android.AndroidEntryPoint


const val LOCATION_REQUEST_CODE = 123456
private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val sharedViewModel by viewModels<SharedViewModel>()

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        setViewModels()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED)  {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
        } else if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
                Log.d(TAG, "lastLocation: $location")
                location?.let {
                    sharedViewModel.lastLatLonLiveData.value = LatLon(location.latitude, location.longitude)
                } ?: run {
                    sharedViewModel.lastLatLonLiveData.value = LatLon(51.5085, -0.1257)
                }
            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getLastLocation {
                        sharedViewModel.lastLatLonLiveData.value = LatLon(51.5085, -0.1257)
                    }
                } else {
                    sharedViewModel.lastLatLonLiveData.value = LatLon(51.5085, -0.1257)
                }
                return
            }
        }
    }

    internal fun getLastLocation(denied: () -> Unit = {}) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    Log.d(TAG, "lastLocation: $location")
                    location?.let {
                        sharedViewModel.lastLatLonLiveData.value = LatLon(location.latitude, location.longitude)
                    } ?: denied.invoke()
                }
        } else {
            denied.invoke()
        }
    }

    private fun setViewModels() {
        sharedViewModel.toolbarTitle.observe(this) { title ->
            binding.toolbar.title = title
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return when (item.itemId) {
            R.id.action_toggle -> {
                sharedViewModel.toggleToolbar()
                true
            }
            R.id.action_refresh -> {
                sharedViewModel.actionRefreshClicked.value = true
                true
            }
            R.id.action_list -> {
                navController.navigate(R.id.action_FirstFragment_to_SecondFragment)
                true
            }
            R.id.action_restore -> {
                sharedViewModel.actionRestoreClicked.value = true
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}