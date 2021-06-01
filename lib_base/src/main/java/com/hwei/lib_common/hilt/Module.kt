package com.hwei.lib_common.hilt

import com.hwei.lib_common.BuildConfig
import com.hwei.lib_common.hilt.annotation.RetrofitAnnotation
import com.hwei.lib_common.net.cookie.CookieManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class Module {
    companion object {
        private const val BASE_URL = BuildConfig.BASE_URL
        private const val OTHER_URL = ""
    }

    @Provides
    @Inject fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @RetrofitAnnotation.OtherRetrofit
    @Inject fun provideRetrofit2(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(OTHER_URL)
            .build()
    }


    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    //addNetworkInterceptor(myItercepter())
                }
            }
            .cookieJar(CookieManager())
            .build()
    }
}