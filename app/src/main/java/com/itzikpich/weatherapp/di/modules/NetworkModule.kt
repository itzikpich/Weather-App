package com.itzikpich.weatherapp.di.modules

import com.itzikpich.weatherapp.data.RetrofitService
import com.itzikpich.weatherapp.utilities.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    /**
     *  You can use the @Provides annotation in Dagger modules
     *  to tell Dagger how to provide classes
     *  that your project doesn't own
     *  (e.g. an instance of Retrofit).
     */

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