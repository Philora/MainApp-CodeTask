package com.find.myipapp.repo

import com.find.myipapp.api_content.MyIpApi
import com.find.myipapp.model.MyIpResponse
import retrofit2.Response

class DataSource(val api: MyIpApi) {
    suspend fun callFindMyIp(): Response<MyIpResponse> = api.fetchMyIpAPI()
}
