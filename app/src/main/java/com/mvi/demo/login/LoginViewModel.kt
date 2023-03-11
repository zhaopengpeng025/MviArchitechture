package com.mvi.demo.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvi.demo.data.success
import com.mvi.demo.repository.Repository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/*****************************************
 * 描  述：
 *
 * 作  者： Pudding
 *
 * 日  期： 2023/2/18
 *****************************************/
class LoginViewModel(private val repository: Repository) : ViewModel() {

  private val _uiState = MutableStateFlow(LoginState())
  val uiState = _uiState.asStateFlow()

  private val _uiEvent = MutableSharedFlow<LoginViewEvent>()
  val uiEvent = _uiEvent.asSharedFlow()

  fun updateName(name: String) {
    _uiState.update { it.copy(name = name) }
  }

  fun updatePassword(pwd: String) {
    _uiState.update { it.copy(password = pwd) }
  }

  fun login() {
    viewModelScope.launch {
      _uiState.value.also { loginUser ->
        _uiEvent.emit(LoginViewEvent.ShowLoading)
        try {
          val response = repository.login(loginUser.name!!, loginUser.password!!)
          if (response.success) {
            _uiEvent.emit(LoginViewEvent.LoginSuccess(response.data))
          } else {
            _uiEvent.emit(LoginViewEvent.ShowToast(response.errorMsg))
          }
        } catch (e: Exception) {
          e.printStackTrace()
          _uiEvent.emit(LoginViewEvent.ShowToast("Login failed with unknown exception!"))
        } finally {
          _uiEvent.emit(LoginViewEvent.DismissLoading)
        }
      }
    }
  }

}
