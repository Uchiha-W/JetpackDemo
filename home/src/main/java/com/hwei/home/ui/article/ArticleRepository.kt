package com.hwei.home.ui.article

import com.hwei.home.net.HomeService
import com.hwei.lib_base.net.base.Resource
import javax.inject.Inject

class ArticleRepository @Inject constructor(private val homeService: HomeService) {
    suspend fun collect(id: Int): Resource<Any> {
        return homeService.collectArticle(id).resource
    }
}