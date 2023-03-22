package com.mvi.demo.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mvi.demo.data.model.Article

/**
 * ***************************************
 * 描 述：
 *
 * 作 者： Pudding
 *
 * 日 期： 2023/2/26
 * ***************************************
 */
@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class WanDatabase : RoomDatabase() {
  abstract fun getArticleDao(): ArticleDao

  companion object {
    @Volatile private var instance: WanDatabase? = null

    fun getInstance(context: Context): WanDatabase {
      return instance
          ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }
    }

    private fun buildDatabase(context: Context): WanDatabase {
      return Room.databaseBuilder(context, WanDatabase::class.java, "wanandroid-db").build()
    }
  }
}
