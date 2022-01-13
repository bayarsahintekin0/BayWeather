package com.bayarsahintekin.bayweather.di

import android.content.Context
import com.bayarsahintekin.bayweather.data.local.AppDatabase
import com.bayarsahintekin.bayweather.data.local.ProductsDao
import com.bayarsahintekin.bayweather.data.remote.IService
import com.bayarsahintekin.bayweather.data.remote.RemoteDataSource
import com.bayarsahintekin.bayweather.data.repository.WeatherRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.DefineComponent
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager

import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.Interceptor

import okhttp3.OkHttpClient
import okhttp3.Request


@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val request: Request =
                chain.request().newBuilder().addHeader("x-rapidapi-host", "weatherbit-v1-mashape.p.rapidapi.com")
                    .addHeader("x-rapidapi-key", "c1b0fe17d1msh75c81f3e14e2c52p1d299bjsnf92cfca07bc1")
                    .build()
            chain.proceed(request)
        }

       return Retrofit.Builder()
            .baseUrl("https://weatherbit-v1-mashape.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideCharacterService(retrofit: Retrofit): IService = retrofit.create(IService::class.java)

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(service: IService) = RemoteDataSource(service)
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase) = db.productsDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: RemoteDataSource,
                          localDataSource: ProductsDao
    ) =
        WeatherRepository(remoteDataSource, localDataSource)
}