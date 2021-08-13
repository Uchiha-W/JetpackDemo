package com.hwei.me.ui.persional

import com.hwei.lib_base.base.BaseViewModel
import com.hwei.lib_base.net.flowRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(private val personRepository: PersonRepository) :
    BaseViewModel() {

    fun logout(): Flow<Any> {
        return flowRequest<Any> {
            onRequest {
                personRepository.logout()
            }
        }
    }

}