package com.itzikpich.weatherapp.di.modules

import com.itzikpich.weatherapp.data.RetrofitService
import com.itzikpich.weatherapp.utilities.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitService(
        client: OkHttpClient
    ) : RetrofitService {

        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }

    @Provides
    fun httpLogger() : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun httpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ) : OkHttpClient = OkHttpClient.Builder().apply {
        addNetworkInterceptor(loggingInterceptor)
    }.build()

}