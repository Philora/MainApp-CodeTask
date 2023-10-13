package com.find.myipapp

import com.find.myipapp.api_content.MyIpApi
import com.find.myipapp.common.CheckBasicTesting
import com.find.myipapp.repo.DataSource
import com.find.myipapp.model.MyIpResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class DataSourceTest : CheckBasicTesting() {

    private val myIpResponse = MyIpResponse("136.226.255.75")

    @Mock
    lateinit var ipApi: MyIpApi

    private lateinit var dataSource: DataSource

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        dataSource = DataSource(ipApi)
    }

    @Test
    fun testingMyIpSuccess() = runTest {
        Mockito.`when`(ipApi.fetchMyIpAPI()).thenReturn(Response.success(myIpResponse))
        val result = dataSource.callFindMyIp()
        Assert.assertEquals("136.226.255.75", result.body()!!.ip)
    }

    @Test
    fun testingMyIpFailure() = runTest {
        Mockito.`when`(ipApi.fetchMyIpAPI()).thenReturn(Response.success(myIpResponse))
        val result = dataSource.callFindMyIp()
        Assert.assertEquals("123.456.789.00", result.body()!!.ip)
    }

    @Test
    fun testMyIpValue() = runTest {
        val myIpApi = mockk<MyIpApi>()
        val ipDataSource = DataSource(myIpApi)
        coEvery { myIpApi.fetchMyIpAPI() } returns mockk()
        ipDataSource.callFindMyIp()
        coVerify { myIpApi.fetchMyIpAPI() }
    }
}
