package com.example.furlencotask.di

import com.example.furlencotask.data.constants.AppConstants
import com.example.furlencotask.data.RepoImpl
import com.example.furlencotask.data.services.networkRequests.GetServices
import com.example.furlencotask.domain.Repository
import com.example.furlencotask.domain.usecases.GetNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
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
    fun provideRetrofitInstance(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).baseUrl(AppConstants.BASE_URL)
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS).build()
    }

    @Provides
    @Singleton
    fun getRepoImpl(getService:GetServices): RepoImpl{
        return RepoImpl(getService)
    }

    // Services
    @Provides
    @Singleton
    fun getRequestsService(retrofit: Retrofit): GetServices {
        return retrofit.create(GetServices::class.java)
    }


    // Use cases
    @Provides
    fun provideNewsResultsUseCase(repository: Repository): GetNewsUseCase {
        return GetNewsUseCase(repository)
    }
}