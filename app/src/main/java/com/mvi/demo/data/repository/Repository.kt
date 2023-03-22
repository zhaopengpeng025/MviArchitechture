package com.mvi.demo.data.repository

import com.mvi.demo.data.model.Article
import com.mvi.demo.data.model.success
import com.mvi.demo.data.datasource.local.ArticleDao
import com.mvi.demo.data.datasource.remote.WanAndroidApi
import kotlinx.coroutines.flow.Flow

/**
 * **************************************
 * 描 述：
 *
 * 作 者： Pudding
 *
 * 日 期： 2023/2/18
 * ***************************************
 */
class Repository(private val api: WanAndroidApi, private val dao: ArticleDao) {

  suspend fun login(name: String, password: String) = api.login(name, password)

  fun fetchLocalArticles(): Flow<List<Article>> = dao.getAll()

  suspend fun fetchRemoteArticles(page: Int = 0): Result<Any> {
    return try {
      val resp = api.fetchArticles(page)
      if (resp.success) {
        dao.saveArticles(resp.data.datas)
        Result.success(Unit)
      } else {
        Result.failure(Exception(resp.errorMsg))
      }
    } catch (e: Exception) {
      e.printStackTrace()
      Result.failure(e)
    }
  }
}
