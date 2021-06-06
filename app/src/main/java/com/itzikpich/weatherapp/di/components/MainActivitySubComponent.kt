package com.itzikpich.weatherapp.di.components

//import com.itzikpich.weatherapp.CitiesFragment
//import com.itzikpich.weatherapp.HomeFragment
import com.itzikpich.weatherapp.CitiesFragment
import com.itzikpich.weatherapp.CityDetailsFragment
import com.itzikpich.weatherapp.HomeFragment
import com.itzikpich.weatherapp.MainActivity
import com.itzikpich.weatherapp.di.scopes.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface MainActivitySubComponent {

    // Factory that is used to create instances of this subcomponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): MainActivitySubComponent
    }

    /** This tells Dagger that JobsActivity requests injection from JobsComponent
     * so that this subcomponent graph needs to satisfy all the dependencies of the
     * fields that JobsActivity is injecting
     */
    fun inject(activity: MainActivity)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: CitiesFragment)
    fun inject(fragment: CityDetailsFragment)
}