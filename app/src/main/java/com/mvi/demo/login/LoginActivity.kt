package com.mvi.demo.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.ViewModelInitializer
import com.mvi.demo.databinding.LayoutLoginBinding
import com.mvi.demo.data.datasource.local.WanDatabase
import com.mvi.demo.data.datasource.remote.WanAndroidApi
import com.mvi.demo.extensions.observeState
import com.mvi.demo.home.HomeActivity
import com.mvi.demo.data.repository.Repository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * **************************************
 * 描 述：
 *
 * 作 者： Pudding
 *
 * 日 期： 2023/2/18
 * ***************************************
 */
class LoginActivity : AppCompatActivity() {

  private lateinit var binding: LayoutLoginBinding

  private val viewModel by viewModels<LoginViewModel> {
    ViewModelProvider.Factory.from(
      ViewModelInitializer(LoginViewModel::class.java) {
        LoginViewModel(
          Repository(
            WanAndroidApi.create(),
            WanDatabase.getInstance(this@LoginActivity).getArticleDao()
          )
        )
      })
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    ViewModelProvider(this, ViewModelProvider.Factory.from())
    binding = LayoutLoginBinding.inflate(LayoutInflater.from(this))
    setContentView(binding.root)

    initListener()
    initState()
    initEvent()
  }

  private fun initListener() {
    binding.editUserName.addTextChangedListener { viewModel.updateName(it.toString().trim()) }
    binding.editPassword.addTextChangedListener { viewModel.updatePassword(it.toString().trim()) }
    binding.btnLogin.setOnClickListener { viewModel.login() }
  }

  private fun initState() {
    viewModel.uiState.observeState(this, LoginState::name) {
      if (it?.isNotBlank() == true && it.length < 5) {
        binding.ilUserName.error = "User name contains more than 5 characters"
      } else {
        binding.ilUserName.error = null
      }
    }

    viewModel.uiState.observeState(this, LoginState::password) {
      if (it?.isNotBlank() == true && it.length < 6) {
        binding.ilPassword.error = "Password contains more than 6 characters"
      } else {
        binding.ilPassword.error = null
      }
    }

    viewModel.uiState.observeState(this, LoginState::inputValid) { binding.btnLogin.isEnabled = it }
  }

  private fun initEvent() {
    MainScope().launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.uiEvent.collect {
          when (it) {
            is LoginViewEvent.ShowLoading -> binding.progress.visibility = View.VISIBLE
            is LoginViewEvent.DismissLoading -> binding.progress.visibility = View.GONE
            is LoginViewEvent.ShowToast ->
              Toast.makeText(this@LoginActivity, it.message, Toast.LENGTH_SHORT).show()
            is LoginViewEvent.LoginSuccess -> {
              startActivity(
                Intent(this@LoginActivity, HomeActivity::class.java).apply {
                  putExtra("user", it.user)
                })
              finish()
            }
          }
        }
      }
    }
  }
}
