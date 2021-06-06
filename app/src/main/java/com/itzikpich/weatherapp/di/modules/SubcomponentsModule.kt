package com.itzikpich.weatherapp.di.modules

import com.itzikpich.weatherapp.di.components.MainActivitySubComponent
import dagger.Module

// The "subcomponents" attribute in the @Module annotation tells Dagger what
// Subcomponents are children of the Component this module is included in.
@Module(subcomponents = [MainActivitySubComponent::class])
class SubcomponentsModule {
}