package com.find.myipapp.api_content // ktlint-disable package-name

import com.find.myipapp.model.MyIpResponse
import retrofit2.Response
import retrofit2.http.GET

interface MyIpApi {
    @GET("json/")
    suspend fun fetchMyIpAPI(): Response<MyIpResponse>
}
