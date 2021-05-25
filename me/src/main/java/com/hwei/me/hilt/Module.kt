package com.hwei.me.hilt

import com.hwei.me.net.MeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Inject

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelMovieModule {
    @Provides
    @Inject
    fun provideRepo(retrofit: Retrofit): MeService {
        return retrofit.create(MeService::class.java)
    }
}