package com.mvi.demo.home

import com.mvi.demo.data.Article

/*****************************************
 * 描  述：
 *
 * 作  者： Pudding
 *
 * 日  期： 2023/2/18
 *****************************************/
data class HomeState (
  val articleList: List<Article>? = null
)