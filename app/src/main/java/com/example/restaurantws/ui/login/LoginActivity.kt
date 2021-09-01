package com.example.restaurantws.ui.login

import android.content.SharedPreferences
import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.restaurantws.R
import com.example.restaurantws.core.Resource
import com.example.restaurantws.data.auth.models.LoginRequest
import com.example.restaurantws.databinding.ActivityLoginBinding
import com.example.restaurantws.ui.sign_up.SignUpActivity
import com.example.restaurantws.ui.speciality.SpecialityActivity
import com.example.restaurantws.utils.afterTextChanged
import com.example.restaurantws.utils.goToActivity
import com.example.restaurantws.utils.toast
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModel()
    private val prefs: SharedPreferences by inject()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeForm()
        observeLoginResult()
        setUpInputValidations()

        checkPrefsAndSetInputDataIfNecessary()

        binding.loginBody.btnGoToSignUp.setOnClickListener { goToActivity<SignUpActivity>() }
    }

    private fun checkPrefsAndSetInputDataIfNecessary() {
        val request = LoginRequest.fromJson(prefs.getString(getString(R.string.saved_user), null))

        if (request != null) {
            with(binding.loginBody) {
                loginEmail.setText(request.email)
                loginPassword.setText(request.pwd)
                btnLogin.isEnabled = true
                checkBoxRememberMe.apply {
                    isEnabled = true
                    isChecked = true
                }
            }
        }
    }

    private fun setUpInputValidations() = with(binding.loginBody) {

        loginEmail.afterTextChanged {
            loginViewModel.loginDataChanged(
                loginEmail.text.toString(),
                loginPassword.text.toString()
            )
        }

        loginPassword.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    loginEmail.text.toString(),
                    loginPassword.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            LoginRequest(
                                loginEmail.text.toString(),
                                loginPassword.text.toString()
                            )
                        )
                }
                false
            }

            btnLogin.setOnClickListener {
                loginViewModel.login(
                    LoginRequest(
                        loginEmail.text.toString(),
                        loginPassword.text.toString()
                    )
                )
            }
        }
    }

    private fun observeLoginResult() {
        lifecycleScope.launchWhenStarted {
            loginViewModel.loginResult.collect {
                when (it) {
                    is Resource.Empty -> {
                    }
                    is Resource.Error -> with(binding) {
                        progressBarLogin.isVisible = false
                        loginBody.root.isVisible = true
                        toast(it.error?.message)
                    }
                    is Resource.Loading -> with(binding) {
                        progressBarLogin.isVisible = true
                        loginBody.root.isVisible = false
                    }

                    is Resource.Success -> {
                        if (binding.loginBody.checkBoxRememberMe.isChecked) saveRememberMeUserCredentials(
                            it.data!!
                        )

                        saveUserCredentials()

                        goToActivity<SpecialityActivity>()
                        finish()
                    }
                }
            }
        }
    }

    private fun saveUserCredentials() {
        with(prefs.edit())
        {
            putBoolean(getString(R.string.is_saved_current_user), true)
            commit()
        }
    }

    private fun saveRememberMeUserCredentials(data: LoginRequest) {
        with(prefs.edit())
        {
            putString(getString(R.string.saved_user), data.toJson())
            commit()
        }
    }

    private fun observeForm() = with(binding.loginBody) {
        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            btnLogin.isEnabled = loginState.isDataValid
            checkBoxRememberMe.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                loginEmail.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                loginPassword.error = getString(loginState.passwordError)
            }
        })
    }

}

