package com.hwei.system.hilt

import com.hwei.system.net.SystemService
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
    fun provideService(retrofit: Retrofit): SystemService {
        return retrofit.create(SystemService::class.java)
    }
}