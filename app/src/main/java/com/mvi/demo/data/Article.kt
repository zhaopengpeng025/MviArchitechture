package com.mvi.demo.data

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

/**
 * **************************************
 * 描 述：
 *
 * 作 者： Pudding
 *
 * 日 期： 2023/2/26
 * ***************************************
 */
@Entity(tableName = "article")
data class Article(
  @PrimaryKey(autoGenerate = true) val primaryId: Int,
  val apkLink: String,
  val audit: Int,
  val author: String,
  val canEdit: Boolean = false,
  val chapterId: Int,
  val chapterName: String,
  val collect: Boolean = false,
  val courseId: Int,
  val desc: String,
  val descMd: String,
  val envelopePic: String,
  val fresh: Boolean = false,
  val host: String,
  val id: Int,
  val isAdminAdd: Boolean = false,
  val link: String,
  val niceDate: String,
  val niceShareDate: String,
  val origin: String,
  val prefix: String,
  val projectLink: String,
  val publishTime: Long,
  val realSuperChapterId: Int,
  val route: Boolean = false,
  val selfVisible: Int,
  val shareDate: Long,
  val shareUser: String,
  val superChapterId: Int,
  val superChapterName: String,
//  val tags: List<Tag>,
  val title: String,
  val type: Int,
  val userId: Int,
  val visible: Int,
  val zan: Int
)

data class Tag(val name: String, val url: String)
