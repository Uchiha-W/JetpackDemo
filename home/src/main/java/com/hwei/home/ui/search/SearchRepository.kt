package com.hwei.home.ui.search

import com.hwei.home.bean.Article
import com.hwei.home.net.HomeService
import com.hwei.lib_base.net.base.BasePage
import com.hwei.lib_base.net.base.Resource
import javax.inject.Inject

class SearchRepository @Inject constructor(private val homeService: HomeService) {

    suspend fun search(page: Int, content: String): Resource<BasePage<Article>> {
        return homeService.search(page, content).resource
    }
}