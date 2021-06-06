package com.itzikpich.weatherapp

import android.app.Application
import com.itzikpich.weatherapp.di.components.DaggerApplicationComponent

class App: Application() {

    val appComponent = DaggerApplicationComponent.builder().create(this).build()

}