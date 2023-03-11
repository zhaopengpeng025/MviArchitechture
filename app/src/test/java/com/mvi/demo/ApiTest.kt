package com.mvi.demo

import com.mvi.demo.data.BaseResponse
import com.mvi.demo.data.success
import com.mvi.demo.datasource.remote.WanAndroidApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/*****************************************
 * 描  述：
 *
 * 作  者： Pudding
 *
 * 日  期： 2023/2/18
 *****************************************/
@OptIn(ExperimentalCoroutinesApi::class)
class ApiTest {

  private val api: WanAndroidApi by lazy { WanAndroidApi.create() }


  @Test
  fun register() = runTest {
    api.register("zpp", "1", "1").testFailure()
    api.register("zpp", "123456", "234567").testFailure()
    api.register("zpp","111111","111111").testFailure()
  }


  @Test
  fun loginSuccess() = runTest {
    val result = api.login("zpptest", "000000")
    println(result)
    assertTrue(result.success)
  }

  @Test
  fun loginFailed() = runTest {
    api.login("zpptest", "00110000").testFailure()
  }

  @Test
  fun fetchArticle() = runTest {
    api.fetchArticles(0)
      .testTrue()
  }

  private fun BaseResponse<*>.testTrue(){
    println(this)
    assertTrue(success)
  }

  private fun BaseResponse<*>.testFailure(){
    println(this)
    assertFalse(success)
  }
}