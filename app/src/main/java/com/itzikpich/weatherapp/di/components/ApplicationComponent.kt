package com.itzikpich.weatherapp.di.components

import android.app.Application
import com.itzikpich.weatherapp.App
import com.itzikpich.weatherapp.di.modules.GsonModule
import com.itzikpich.weatherapp.di.modules.NetworkModule
import com.itzikpich.weatherapp.di.modules.SubcomponentsModule
import com.itzikpich.weatherapp.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(modules = [
    ViewModelModule::class,
    SubcomponentsModule::class,
    GsonModule::class,
    NetworkModule::class,
])
interface ApplicationComponent: AndroidInjector<App> {

    fun mainActivitySubComponent(): MainActivitySubComponent.Factory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(app: Application): Builder
        fun build(): ApplicationComponent
    }

}