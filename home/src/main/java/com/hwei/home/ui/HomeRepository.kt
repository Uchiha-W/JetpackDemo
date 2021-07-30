package com.hwei.home.ui

import com.hwei.home.bean.Article
import com.hwei.home.bean.BannerBean
import com.hwei.home.net.HomeService
import com.hwei.lib_base.net.base.BasePage
import com.hwei.lib_base.net.base.Resource
import javax.inject.Inject

class HomeRepository @Inject constructor(private val homeService: HomeService) {
    suspend fun getArticleList(page: Int): BasePage<Article> {
        return homeService.getArticleList(page).data
    }

    suspend fun getBannerList(): Resource<List<BannerBean>> {
        return homeService.getBannerList().resource
    }
}