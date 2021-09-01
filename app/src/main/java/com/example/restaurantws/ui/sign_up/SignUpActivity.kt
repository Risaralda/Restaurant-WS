package com.example.restaurantws.ui.sign_up

import android.content.SharedPreferences
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.restaurantws.core.Resource
import com.example.restaurantws.data.auth.models.LoginRequest
import com.example.restaurantws.data.auth.models.User
import com.example.restaurantws.databinding.ActivitySignUpBinding
import com.example.restaurantws.ui.login.LoginViewModel
import com.example.restaurantws.ui.speciality.SpecialityActivity
import com.example.restaurantws.utils.afterTextChanged
import com.example.restaurantws.utils.goToActivity
import com.example.restaurantws.utils.toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : AppCompatActivity() {

    private val signUpViewModel: SignUpViewModel by viewModel()
    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var binding: ActivitySignUpBinding

    private val prefs: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeForm()
        observePrivacyPoliciesResult()
        observeSignUpResult()
        setUpInputValidations()

        signUpViewModel.getPolicies()
    }

    private fun observePrivacyPoliciesResult() {
        lifecycleScope.launchWhenStarted {
            signUpViewModel.policiesResult.collect {
                when (it) {
                    is Resource.Error -> {
                        toast(it.error?.message)
                        finish()
                    }
                    else -> {
                    }
                }
            }
        }
    }

    private fun setUpInputValidations() = with(binding.signUpBody) {

        signUpEmail.afterTextChanged {
            signUpViewModel.signUpDataChanged(
                buildSignUpRequest()
            )
        }
        signUpPassword.afterTextChanged {
            signUpViewModel.signUpDataChanged(
                buildSignUpRequest()
            )
        }

        signUpName.afterTextChanged {
            signUpViewModel.signUpDataChanged(
                buildSignUpRequest()
            )
        }

        signUpCity.apply {
            afterTextChanged {
                signUpViewModel.signUpDataChanged(
                    buildSignUpRequest()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        showPoliciesDialog()
                }
                false
            }

            btnSignUp.setOnClickListener {
                showPoliciesDialog()
            }
        }
    }

    private fun buildSignUpRequest(): User = with(binding.signUpBody) {
        return User(
            null,
            signUpName.text.toString(),
            signUpEmail.text.toString(),
            signUpPassword.text.toString(),
            signUpCity.text.toString(),
        )
    }

    private fun observeSignUpResult() {
        lifecycleScope.launchWhenStarted {
            signUpViewModel.signUpResult.collect {
                when (it) {
                    is Resource.Empty -> {
                    }
                    is Resource.Error -> with(binding) {
                        progressBarSignUp.isVisible = false
                        signUpBody.root.isVisible = true
                        toast(it.error?.message)
                    }
                    is Resource.Loading -> with(binding) {
                        progressBarSignUp.isVisible = true
                        signUpBody.root.isVisible = false
                    }

                    is Resource.Success -> {
                        doLogin()
                    }
                }
            }
        }
    }

    private fun doLogin() {
        val user = buildSignUpRequest()
        val loginRequest = LoginRequest(
            user.correo,
            user.contrasena
        )

        loginViewModel.login(loginRequest)

        observeLoginResult()
    }

    private fun observeLoginResult() {
        lifecycleScope.launchWhenStarted {
            loginViewModel.loginResult.collect {
                when (it) {
                    is Resource.Empty -> {
                    }
                    is Resource.Error -> with(binding) {
                        progressBarSignUp.isVisible = false
                        signUpBody.root.isVisible = true
                        toast(it.error?.message)
                    }
                    is Resource.Success -> {
                        saveUserCredentials()
                        goToActivity<SpecialityActivity>()
                        finish()
                    }
                    else -> {
                    }
                }
            }

        }
    }

    private fun saveUserCredentials() {
        with(prefs.edit())
        {
            putBoolean(getString(com.example.restaurantws.R.string.is_saved_current_user), true)
            commit()
        }
    }

    private fun observeForm(

    ) = with(binding.signUpBody) {
        signUpViewModel.signUpFormState.observe(this@SignUpActivity, Observer {
            val formState = it ?: return@Observer

            // disable login button unless both username / password is valid
            btnSignUp.isEnabled = formState.isDataValid

            if (formState.emailError != null) {
                signUpEmail.error = getString(formState.emailError)
            }
            if (formState.passwordError != null) {
                signUpPassword.error = getString(formState.passwordError)
            }

            if (formState.nameError != null) {
                signUpName.error = getString(formState.nameError)
            }

            if (formState.cityError != null) {
                signUpCity.error = getString(formState.cityError)
            }


        })
    }

    private fun showPoliciesDialog() {
        MaterialAlertDialogBuilder(this).apply {
            setTitle("Políticas de privacidad")
            setMessage(signUpViewModel.policiesResult.value.data!!.politicas)

            setPositiveButton("Aceptar") { d, _ ->
                signUpViewModel.signUp(buildSignUpRequest())
                d.dismiss()
            }

            setNegativeButton("Rechazar") { d, _ ->
                toast("Su cuenta no fue creada")
                d.dismiss()
            }


            show()
        }
    }

}

