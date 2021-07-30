package com.hwei.me.ui.articleList

import com.hwei.lib_base.net.base.BasePage
import com.hwei.lib_base.net.base.Resource
import com.hwei.me.net.MeService
import javax.inject.Inject

class MyCollectRepository @Inject constructor(private val meService: MeService) {

    suspend fun getMyCollectList(page: Int): Resource<BasePage<String>> {
        return meService.getCollectList(page).resource
    }
}