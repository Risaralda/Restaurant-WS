package com.example.restaurantws.utils

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment


fun Activity.toast(message: String?) {
    Toast.makeText(this, message ?: "Ocurrió un error inesperado", Toast.LENGTH_SHORT).show()
}


inline fun <reified T> Activity.goToActivity() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}


//fun ImageView.load(url: String) {
//    Glide.with(this).load(url).into(this)
//}

fun ImageView.loadDrawable(drawable: Int) {
    setImageDrawable(ResourcesCompat.getDrawable(resources, drawable, null))
}

fun Fragment.toast(message: String?) {
    Toast.makeText(requireContext(), message ?: "Ocurrió un error inesperado", Toast.LENGTH_SHORT)
        .show()
}


fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}