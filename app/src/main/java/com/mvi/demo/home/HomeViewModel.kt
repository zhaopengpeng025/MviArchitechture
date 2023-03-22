package com.mvi.demo.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvi.demo.data.model.NormalEvent
import com.mvi.demo.data.repository.Repository
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
class HomeViewModel(repository: Repository) : ViewModel() {

  private val _uiState = MutableStateFlow(HomeState())
  val uiState = _uiState.asStateFlow()

  private val _uiEvent = MutableSharedFlow<NormalEvent>()
  val uiEvent = _uiEvent.asSharedFlow()


  init {

    viewModelScope.launch {
      repository.fetchLocalArticles()
        .collect{articles ->
          _uiState.update { it.copy(articleList = articles)}
      }
    }

    viewModelScope.launch {
      _uiEvent.emit(NormalEvent.Loading(true))
      repository.fetchRemoteArticles()
      _uiEvent.emit(NormalEvent.Loading(false))
    }
  }

}