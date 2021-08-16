package com.hwei.lib_base.hilt

import com.hwei.lib_base.BuildConfig
import com.hwei.lib_base.net.cookie.CookieManager
import com.hwei.lib_base.net.iterceptor.MultiUrlInterceptor
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

        //用于多baseUrl时替换domain时使用，默认不改变
        internal const val DOMAIN_NAME = "Domain_Name"
        const val DOMAIN_HEADER = "${DOMAIN_NAME}:"
    }

    @Provides
    @Inject
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(MultiUrlInterceptor())
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }
            .cookieJar(CookieManager())
            .build()
    }
}