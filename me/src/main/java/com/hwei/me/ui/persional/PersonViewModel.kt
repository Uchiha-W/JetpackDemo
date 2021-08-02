package com.hwei.me.ui.persional

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hwei.lib_base.base.BaseViewModel
import com.hwei.lib_base.net.request
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(private val personRepository: PersonRepository) :
    BaseViewModel() {

    private val _logout = MutableLiveData<Any>()
    val logout: LiveData<Any>
        get() = _logout

    fun logout() {
        request<Any> {
            onRequest {
                personRepository.logout()
            }
            onSuccess {
                _logout.value = it
            }
        }
    }

}