package com.example.furlencotask.di

import android.app.Application
import com.example.furlencotask.data.RepoImpl
import com.example.furlencotask.data.constants.AppConstants
import com.example.furlencotask.data.services.networkRequests.GetServices
import com.example.furlencotask.AppDatabase
import com.example.furlencotask.domain.Repository
import com.example.furlencotask.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Sourik on 5/11/20.
 */

@InstallIn(ApplicationComponent::class)
@Module
object ClassModules {

    @Provides
    @Singleton
    fun provideRetrofitInstance(httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).baseUrl(AppConstants.BASE_URL)
            .client(httpClient)
            .build()


    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient().newBuilder().connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS).build()


    @Provides
    @Singleton
    fun getRepoImpl(database: AppDatabase, getServices: GetServices): RepoImpl =
        RepoImpl(database, getServices)

    @Provides
    fun provideAppDataBase(application: Application) = AppDatabase.getInstance(application)

    // Services
    @Provides
    @Singleton
    fun getRequestsService(retrofit: Retrofit): GetServices =
        retrofit.create(GetServices::class.java)

    // Use cases
    @Provides
    fun provideNewsResultsUseCase(repository: Repository): GetNewsUseCase =
        GetNewsUseCase(repository)

    @Provides
    fun provideInsertNewsUseCase(repository: Repository): InsertNewsUseCase =
        InsertNewsUseCase(repository)

    @Provides
    fun provideNewsUseCaseForLocal(repository: Repository): GetNewsFromLocalUseCase =
        GetNewsFromLocalUseCase(repository)

    @Provides
    fun getFavouritesUseCase(repository: Repository): GetFavouritesUseCase =
        GetFavouritesUseCase(repository)

    @Provides
    fun updateFavouriteUseCase(repository: Repository): UpdateFavouriteValueUseCase =
        UpdateFavouriteValueUseCase(repository)

}