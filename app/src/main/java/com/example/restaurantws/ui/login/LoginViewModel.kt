package com.example.restaurantws.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.restaurantws.data.LoginRepository

import com.example.restaurantws.R
import com.example.restaurantws.core.Resource
import com.example.restaurantws.data.auth.models.LoginRequest
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableStateFlow<Resource<Boolean>>(Resource.Empty())
    val loginResult: StateFlow<Resource<Boolean>> = _loginResult

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            loginRepository.login(loginRequest)
                .onStart { _loginResult.value = Resource.Loading() }
                .catch { _loginResult.value = Resource.Error(it) }
                .collect { _loginResult.value = Resource.Success(it) }
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