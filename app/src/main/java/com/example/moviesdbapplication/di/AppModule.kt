package com.example.moviesdbapplication.di

import com.example.moviesdbapplication.core.data.module.NetworkModule
import com.example.moviesdbapplication.data.remote.api.MovieApiService
import com.example.moviesdbapplication.data.repository.MovieRepositoryImp
import com.example.moviesdbapplication.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideHomeApi(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideHomeRepository(homeService: MovieApiService): MovieRepository {
        return MovieRepositoryImp(homeService)
    }
}