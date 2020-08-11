package com.example.dogslovecatfact.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClients {
    fun provideClient(): OkHttpClient? {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
    fun provideRetrofit(baseURL: String?): Retrofit {
        return getRetrofitBuilder(baseURL!!, provideClient()!!)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getRetrofitBuilder(
        baseURL: String,
        client: OkHttpClient
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

}