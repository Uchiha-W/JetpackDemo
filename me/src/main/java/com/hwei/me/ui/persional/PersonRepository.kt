package com.hwei.me.ui.persional

import com.hwei.lib_base.net.base.Resource
import com.hwei.me.net.MeService
import javax.inject.Inject

class PersonRepository @Inject constructor(private val meService: MeService) {
    suspend fun logout(): Resource<Any> {
        return meService.logout().resource
    }
}