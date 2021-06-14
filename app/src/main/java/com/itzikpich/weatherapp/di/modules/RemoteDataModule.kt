package com.itzikpich.weatherapp.di.modules

import com.itzikpich.weatherapp.data.RemoteDataSource
import com.itzikpich.weatherapp.data.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataModule {

    @Binds
    abstract fun bindRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ) : RemoteDataSource

}