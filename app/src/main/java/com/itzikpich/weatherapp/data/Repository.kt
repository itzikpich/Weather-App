package com.itzikpich.weatherapp.data

import javax.inject.Inject
import javax.inject.Singleton
import com.itzikpich.weatherapp.data.RemoteDataSource

@Singleton
class Repository @Inject constructor(val remoteDataSourceImpl: RemoteDataSourceImpl) : RemoteDataSource by remoteDataSourceImpl{
}