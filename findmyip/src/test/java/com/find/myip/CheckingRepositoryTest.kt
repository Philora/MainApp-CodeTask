package com.find.myipapp

import com.find.myipapp.repo.DataSource
import com.find.myipapp.repo.MyRepository
import com.find.myipapp.model.MyIpResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class CheckingRepositoryTest {

    private lateinit var myRepository: MyRepository
    private val dataSource = mockk<DataSource>()

    @Before
    fun setUp() {
        myRepository = MyRepository(dataSource)
    }

    @Test
    fun findMyIpUsingMock(): Unit = runBlocking {
        val response = Response.success(MyIpResponse())
        coEvery { dataSource.callFindMyIp() } returns response
        myRepository.findMyIp()
        coVerify { dataSource.callFindMyIp() }
        assertEquals(response.body(), myRepository.findMyIp().data)
    }
}
