package com.orlinskas.cocktail.network

import com.google.gson.GsonBuilder
import com.orlinskas.cocktail.network.api.CocktailApi
import com.orlinskas.cocktail.network.interceptor.ConnectionInterceptor
import com.orlinskas.cocktail.network.interceptor.HeaderInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        headerInterceptor: HeaderInterceptor,
        connectionInterceptor: ConnectionInterceptor
    ): Retrofit {
        val builder = OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(connectionInterceptor)
            .connectTimeout(25, TimeUnit.SECONDS)
            .readTimeout(25, TimeUnit.SECONDS)
            .writeTimeout(25, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl(Urls.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(newGsonInstance()))
            .client(AppInitializer.initNetInterceptors(builder).build())
            .build()
    }

    @Provides
    fun provideCocktailApi(retrofit: Retrofit): CocktailApi = retrofit.create(CocktailApi::class.java)

    private fun newGsonInstance() = GsonBuilder()
        .registerTypeAdapter(Boolean::class.java, BooleanGsonAdapter())
        .create()
}
