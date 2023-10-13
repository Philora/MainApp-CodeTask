package com.find.myipapp.repo

import com.find.myipapp.model.MyIpResponse

class MyRepository(private val dataSource: DataSource) {

    suspend fun findMyIp(): Resource<MyIpResponse> {
        val response = dataSource.callFindMyIp()

        return if (response.isSuccessful) {
            response.body()?.let { result ->
                Resource.Success(result)
            } ?: kotlin.run {
                Resource.Failure("Error occurred")
            }
        } else {
            Resource.Failure("Error occurred")
        }
    }
}
