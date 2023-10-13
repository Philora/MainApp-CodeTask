package com.find.myipapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.find.myipapp.model.MyIpResponse
import com.find.myipapp.repo.MyRepository
import com.find.myipapp.repo.Resource
import kotlinx.coroutines.launch

class MyIpViewModel(private val myRepository: MyRepository) : ViewModel() {

    private var _MyIpResponse: MutableLiveData<Resource<MyIpResponse>> = MutableLiveData()
    val myIpResponse: LiveData<Resource<MyIpResponse>> = _MyIpResponse

    fun findMyIp() {
        viewModelScope.launch {
            callFindMyIPApi()
        }
    }

    private suspend fun callFindMyIPApi() {
        _MyIpResponse.postValue(Resource.Loading())
        val response = myRepository.findMyIp()
        _MyIpResponse.postValue(response)
    }
}
