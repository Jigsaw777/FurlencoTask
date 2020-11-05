package com.example.furlencotask.di

import com.example.furlencotask.data.RepoImpl
import com.example.furlencotask.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/**
 * Created by Sourik on 5/11/20.
 */

@InstallIn(ApplicationComponent::class)
@Module
abstract class InterfaceModules {

    @Binds
    abstract fun bindRepository(implementationClass: RepoImpl): Repository
}