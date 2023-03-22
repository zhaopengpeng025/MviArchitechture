package com.mvi.demo.home

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelInitializer
import com.mvi.demo.R
import com.mvi.demo.data.model.Article
import com.mvi.demo.data.model.User
import com.mvi.demo.databinding.ActivityHomeBinding
import com.mvi.demo.data.datasource.local.WanDatabase
import com.mvi.demo.data.datasource.remote.WanAndroidApi
import com.mvi.demo.data.repository.Repository

/**
 * **************************************
 * 描 述：
 *
 * 作 者： Pudding
 *
 * 日 期： 2023/2/18
 * ***************************************
 */
class HomeActivity : AppCompatActivity() {

  private lateinit var binding: ActivityHomeBinding

  private val viewModel by
      viewModels<HomeViewModel> {
        ViewModelProvider.Factory.from(
            ViewModelInitializer(HomeViewModel::class.java) {
              HomeViewModel(
                  Repository(
                      WanAndroidApi.create(),
                      WanDatabase.getInstance(this@HomeActivity).getArticleDao())
              )
            })
      }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityHomeBinding.inflate(LayoutInflater.from(this))
    val user = intent.getParcelableExtra<User>("user")!!
    setSupportActionBar(binding.toolbar)
    setContent { HomeScreen(user = user) }
  }

  @Composable
  fun HomeScreen(user: User) {
    val uiState by viewModel.uiState.collectAsState()
    Column(Modifier.fillMaxSize()) {
      Row() {
        Icon(
            painter = painterResource(id = R.drawable.ic_user),
            contentDescription = "",
            Modifier.size(20.dp))
        Text(text = user.nickname)
      }
      Surface(Modifier.fillMaxSize()) {
        uiState.articleList?.let { list ->
          LazyColumn {
            items(list.size) { index ->
              ArticleItem(article = list[index])
              Divider()
            }
          }
        }
      }
    }
  }

  @Composable
  fun ArticleItem(article: Article) {
    Column(
        Modifier.height(80.dp).fillMaxWidth().padding(start = 15.dp, end = 15.dp),
        verticalArrangement = Arrangement.Center) {
          Text(
              text = article.title,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.SansSerif,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis)
          Row(Modifier.padding(top = 10.dp)) {
            val authorOrShare =
                with(article) {
                  if (author.isBlank()) {
                    "分享人：$shareUser"
                  } else {
                    "作者：$author"
                  }
                }
            Text(text = authorOrShare, Modifier.padding(end = 20.dp), fontSize = 10.sp)
            Text(text = "分类：${article.superChapterName}/${article.chapterName}", fontSize = 10.sp)
          }
        }
  }
}
