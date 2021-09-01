package com.example.restaurantws.ui.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.restaurantws.data.auth.AuthRepository

import com.example.restaurantws.R
import com.example.restaurantws.core.Resource
import com.example.restaurantws.data.auth.models.Policies
import com.example.restaurantws.data.auth.models.User
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SignUpViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _signUpForm = MutableLiveData<SignUpFormState>()
    val signUpFormState: LiveData<SignUpFormState> = _signUpForm

    private val _policiesResult = MutableStateFlow<Resource<Policies>>(Resource.Empty())
    val policiesResult: StateFlow<Resource<Policies>> = _policiesResult

    private val _signUpResult = MutableStateFlow<Resource<Unit>>(Resource.Empty())
    val signUpResult: StateFlow<Resource<Unit>> = _signUpResult

    fun signUp(user: User) {
        viewModelScope.launch {
            authRepository.signUp(user)
                .onStart { _signUpResult.value = Resource.Loading() }
                .catch { _signUpResult.value = Resource.Error(it) }
                .collect { _signUpResult.value = Resource.Success(it) }
        }
    }

    fun signUpDataChanged(user: User) {
        var haveError = false
        if (!isUserNameValid(user.correo)) {
            _signUpForm.value = SignUpFormState(emailError = R.string.invalid_username)
            haveError = true
        }
        if (!isPasswordValid(user.contrasena)) {
            _signUpForm.value = SignUpFormState(passwordError = R.string.invalid_password)
            haveError = true
        }
        if (user.nombre.isEmpty()) {
            _signUpForm.value = SignUpFormState(nameError = R.string.invalid_name)
            haveError = true
        }
        if (user.ciudad.isEmpty()) {
            _signUpForm.value = SignUpFormState(cityError = R.string.invalid_city)
            haveError = true
        }


        _signUpForm.value = SignUpFormState(isDataValid = !haveError)

    }

    private fun isUserNameValid(username: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    fun getPolicies() {
        viewModelScope.launch {
            authRepository.getPrivacyPolicies()
                .onStart { _policiesResult.value = Resource.Loading() }
                .catch { _policiesResult.value = Resource.Error(exception = it) }
                .collect { _policiesResult.value = Resource.Success(it) }
        }
    }
}