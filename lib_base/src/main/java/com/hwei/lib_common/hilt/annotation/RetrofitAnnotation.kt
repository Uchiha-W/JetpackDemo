package com.hwei.lib_common.hilt.annotation

import javax.inject.Qualifier

class RetrofitAnnotation {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MainRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class OtherRetrofit
}