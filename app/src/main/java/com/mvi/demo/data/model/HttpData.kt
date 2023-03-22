package com.mvi.demo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*****************************************
 * 文  件：
 * 描  述：
 * 作  者： Pudding
 * 日  期： 2023/2/18
 *****************************************/
data class BaseResponse<T>(
  val data: T,
  val errorCode: Int,
  val errorMsg: String
)

val BaseResponse<*>.success: Boolean get() = this.errorCode == 0

data class PageData<T>(
  val curPage: Int,
  val datas: List<T>,
  val offset: Int,
  val over: Boolean,
  val pageCount: Int,
  val size: Int,
  val total: Int
)

@Parcelize
data class User(
  val admin: Boolean,
  val chapterTops: List<Int>,
  val coinCount: Int,
  val collectIds: List<Int>,
  val email: String,
  val icon: String,
  val id: Int,
  val nickname: String,
  val password: String,
  val publicName: String,
  val token: String,
  val type: Int,
  val username: String
) : Parcelable

