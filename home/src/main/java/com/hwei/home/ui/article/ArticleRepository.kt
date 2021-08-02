package com.hwei.home.ui.article

import com.hwei.home.net.HomeService
import com.hwei.lib_base.net.base.Resource
import javax.inject.Inject

class ArticleRepository @Inject constructor(private val homeService: HomeService) {

    suspend fun collectInner(id: Int): Resource<Any> {
        return homeService.collectInnerArticle(id).resource
    }

    suspend fun collectOuter(title: String, author: String, link: String): Resource<Any> {
        return homeService.collectOuterArticle(title, author, link).resource
    }
}