package com.find.myipapp.common

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

open class CheckBasicTesting {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutinesTestRule = MainCoroutineRuleTest()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
}
