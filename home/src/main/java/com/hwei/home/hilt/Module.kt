package com.hwei.home.hilt

import com.hwei.home.net.HomeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Inject


@InstallIn(ViewModelComponent::class)
@Module
class Module {
    @Provides
    @Inject
    fun provideService(retrofit: Retrofit): HomeService {
        return retrofit.create(HomeService::class.java)
    }
}