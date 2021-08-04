package com.hwei.system.ui.system

import com.hwei.lib_base.net.base.BasePage
import com.hwei.lib_base.net.base.Resource
import com.hwei.system.bean.ArticleItem
import com.hwei.system.bean.SystemBeanItem
import com.hwei.system.net.SystemService
import javax.inject.Inject

class SystemRepository @Inject constructor(private val systemService: SystemService) {
    suspend fun getSystemData(): Resource<List<SystemBeanItem>> {
        return systemService.getSystemData().resource
    }

    suspend fun getArticleList(page: Int, id: Int): Resource<BasePage<ArticleItem>> {
        return systemService.getArticleList(page, id).resource
    }
}