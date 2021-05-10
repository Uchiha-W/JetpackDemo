package com.hwei.home.hilt

import com.hwei.home.net.HomeApi
import com.hwei.lib_common.net.RetrofitManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@InstallIn(ViewModelComponent::class)
@Module
class Module {
    @Provides
    fun provideService(): HomeApi {
        return RetrofitManager.create(HomeApi::class.java)
    }
}