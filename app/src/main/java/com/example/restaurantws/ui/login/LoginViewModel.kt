package com.example.restaurantws.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.restaurantws.data.auth.AuthRepository

import com.example.restaurantws.R
import com.example.restaurantws.core.CurrentUser
import com.example.restaurantws.core.Resource
import com.example.restaurantws.data.auth.models.LoginRequest
import com.example.restaurantws.data.auth.models.User
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableStateFlow<Resource<LoginRequest>>(Resource.Empty())
    val loginResult: StateFlow<Resource<LoginRequest>> = _loginResult

    lateinit var user: User

    fun login(loginRequest: LoginRequest) {
        if (_loginResult.value is Resource.Loading) return

        viewModelScope.launch {
            authRepository.login(loginRequest)
                .onStart { _loginResult.value = Resource.Loading() }
                .catch { _loginResult.value = Resource.Error(it) }
                .collect {
                    setCurrentUserData(it)
                    user = it
                    _loginResult.value = Resource.Success(loginRequest)
                }
        }
    }

    private fun setCurrentUserData(user: User) {
        CurrentUser.apply {
            idCliente = user.id ?: 0
            nombre = user.nombre
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}