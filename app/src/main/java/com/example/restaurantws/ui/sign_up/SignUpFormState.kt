package com.example.restaurantws.ui.sign_up

/**
 * Data validation state of the login form.
 */
data class SignUpFormState(
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val nameError: Int? = null,
    val cityError: Int? = null,
    val isDataValid: Boolean = false
)