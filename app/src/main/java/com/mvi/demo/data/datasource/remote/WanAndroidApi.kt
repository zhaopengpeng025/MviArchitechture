package com.mvi.demo.data.datasource.remote

import com.mvi.demo.data.model.Article
import com.mvi.demo.data.model.BaseResponse
import com.mvi.demo.data.model.PageData
import com.mvi.demo.data.model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/*****************************************
 * 文  件：
 *
 * 描  述：
 *
 * 作  者： Pudding
 *
 * 日  期： 2023/2/18
 *****************************************/
interface WanAndroidApi {

  @FormUrlEncoded
  @POST("user/register")
  suspend fun register(
    @Field("username") username: String,
    @Field("password") password: String,
    @Field("repassword") repassword: String
  ): BaseResponse<User>

  @FormUrlEncoded
  @POST("/user/login")
  suspend fun login(
    @Field("username") username: String,
    @Field("password") password: String
  ): BaseResponse<User>

  @GET("article/list/{page}/json")
  suspend fun fetchArticles(@Path("page") page: Int): BaseResponse<PageData<Article>>

  companion object {
    private const val BASE_URL = "https://www.wanandroid.com/"

    fun create(): WanAndroidApi {
      val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

      val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

      return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WanAndroidApi::class.java)
    }
  }
}