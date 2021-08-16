package com.hwei.lib_base.hilt.annotation

import javax.inject.Qualifier

@Deprecated("don't use")
class RetrofitAnnotation {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MainRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class OtherRetrofit
}