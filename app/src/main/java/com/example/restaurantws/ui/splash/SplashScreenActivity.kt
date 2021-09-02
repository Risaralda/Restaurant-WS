package com.example.restaurantws.ui.splash

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurantws.R
import com.example.restaurantws.core.CurrentUser
import com.example.restaurantws.data.auth.models.LoginResponse
import com.example.restaurantws.ui.MainActivity
import com.example.restaurantws.ui.login.LoginActivity
import com.example.restaurantws.utils.goToActivity
import com.google.gson.Gson
import org.koin.android.ext.android.inject

class SplashScreenActivity : AppCompatActivity() {
    private val prefs: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            val json = prefs.getString(getString(R.string.is_saved_current_user), null)

            if (json != null) {
                val user = Gson().fromJson(json, LoginResponse::class.java)
                CurrentUser.apply {
                    idCliente = user.idCliente
                    nombre = user.nombre
                    token = user.token
                }
            }

            if (json != null) goToActivity<MainActivity>() else goToActivity<LoginActivity>()

            finish()
        }, 1000)

    }
}