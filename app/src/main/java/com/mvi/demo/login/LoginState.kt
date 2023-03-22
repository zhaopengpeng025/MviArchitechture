package com.mvi.demo.login

import com.mvi.demo.data.model.User

/*****************************************
 * 描  述：
 *
 * 作  者： Pudding
 *
 * 日  期： 2023/2/18
 *****************************************/
data class LoginState(
  val name: String? = null,
  val password: String? = null,
) {
  val inputValid
    get() = name != null
            && password != null
            && name.length >= 5
            && password.length >= 6
}


sealed class LoginViewEvent {
  data class ShowToast(val message: String) : LoginViewEvent()
  object ShowLoading : LoginViewEvent()
  object DismissLoading : LoginViewEvent()
  data class LoginSuccess(val user: User) : LoginViewEvent()
}
