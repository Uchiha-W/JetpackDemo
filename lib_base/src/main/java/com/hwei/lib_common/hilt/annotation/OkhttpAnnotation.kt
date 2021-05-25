package com.hwei.lib_common.hilt.annotation

import javax.inject.Qualifier

class OkhttpAnnotation {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MainOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class OtherOkHttpClient
}