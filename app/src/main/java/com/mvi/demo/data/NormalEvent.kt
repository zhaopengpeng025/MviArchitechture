package com.mvi.demo.data

/*****************************************
 * 描  述：
 *
 * 作  者： Pudding
 *
 * 日  期： 2023/2/18
 *****************************************/
sealed interface NormalEvent {
  data class Loading(val isLoading: Boolean) : NormalEvent
  data class Error(val e: Throwable) : NormalEvent
}