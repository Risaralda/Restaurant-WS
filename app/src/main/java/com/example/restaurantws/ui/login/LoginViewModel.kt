package com.example.restaurantws.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantws.R
import com.example.restaurantws.core.CurrentUser
import com.example.restaurantws.core.Resource
import com.example.restaurantws.data.auth.AuthRepository
import com.example.restaurantws.data.auth.models.LoginRequest
import com.example.restaurantws.data.auth.models.LoginResponse
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableStateFlow<Resource<LoginRequest>>(Resource.Empty())
    val loginResult: StateFlow<Resource<LoginRequest>> = _loginResult

    lateinit var loggedInUser: LoginResponse

    fun login(loginRequest: LoginRequest) {
        if (_loginResult.value is Resource.Loading) return

        viewModelScope.launch {
            authRepository.login(loginRequest)
                .onStart { _loginResult.value = Resource.Loading() }
                .catch { _loginResult.value = Resource.Error(it) }
                .collect {
                    setCurrentUserData(it)
                    loggedInUser = it
                    _loginResult.value = Resource.Success(loginRequest)
                }
        }
    }

    private fun setCurrentUserData(data: LoginResponse) {
        CurrentUser.apply {
            idCliente = data.idCliente
            nombre = data.nombre
            token = data.token

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