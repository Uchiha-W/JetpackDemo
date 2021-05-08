package com.hwei.me.hilt

import com.hwei.lib_common.net.RetrofitManager
import com.hwei.me.net.MeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelMovieModule {
    @Provides
    fun provideRepo(): MeService {
        return RetrofitManager.create(MeService::class.java)
    }
}