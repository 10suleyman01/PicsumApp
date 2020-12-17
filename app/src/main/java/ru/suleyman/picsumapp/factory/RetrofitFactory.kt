package ru.suleyman.picsumapp.factory

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    fun provideGson() = Gson()

    fun provideRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://picsum.photos/v2/")
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    }

}
