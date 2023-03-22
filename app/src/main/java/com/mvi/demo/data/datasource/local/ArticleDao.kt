package com.mvi.demo.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mvi.demo.data.model.Article
import kotlinx.coroutines.flow.Flow

/**
 * **************************************
 * 描 述：
 *
 * 作 者： Pudding
 *
 * 日 期： 2023/2/26
 * ***************************************
 */
@Dao
interface ArticleDao {

  @Query("SELECT * FROM article") fun getAll(): Flow<List<Article>>

  @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun saveArticles(list: List<Article>)
}
