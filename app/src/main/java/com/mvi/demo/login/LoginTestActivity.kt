package com.mvi.demo.login

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mvi.demo.databinding.LayoutLogin2Binding

/**
 * **************************************
 * 描 述：
 *
 * 作 者： Pudding
 *
 * 日 期： 2023/3/4
 * ***************************************
 */
class LoginTestActivity : AppCompatActivity() {

  private lateinit var binding: LayoutLogin2Binding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = LayoutLogin2Binding.inflate(LayoutInflater.from(this))
    setContentView(binding.root)
    binding.compose.setContent {
      PasswordTextField()
    }
  }

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun PasswordTextField() {
    OutlinedTextField(value = "", onValueChange = {}, label = { Text(text = "密码-Compose") })
  }
}
