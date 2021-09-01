package com.example.restaurantws.ui.speciality

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.restaurantws.R
import com.example.restaurantws.core.Resource
import com.example.restaurantws.data.main.models.especialidad.Speciality
import com.example.restaurantws.databinding.ActivitySpecialityBinding
import com.example.restaurantws.ui.MainActivity
import com.example.restaurantws.utils.goToActivity
import com.example.restaurantws.utils.loadDrawable
import com.example.restaurantws.utils.toast
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class
SpecialityActivity : AppCompatActivity() {
    private val specialityViewModel: SpecialityViewModel by viewModel()
    private lateinit var binding: ActivitySpecialityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speciality)

        binding = ActivitySpecialityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeSpecialityResult()
        specialityViewModel.loadSpeciality()

        binding.btnContinue.setOnClickListener { goToActivity<MainActivity>() }
    }

    private fun observeSpecialityResult() {
        lifecycleScope.launchWhenStarted {
            specialityViewModel.signUpResult.collect {
                when (it) {
                    is Resource.Empty -> {
                    }
                    is Resource.Error -> with(binding) {
                        progressBarSpeciality.isVisible = false
                        toast(it.error?.message)
                    }
                    is Resource.Loading -> with(binding) {
                        progressBarSpeciality.isVisible = true
                    }
                    is Resource.Success -> {
                        binding.progressBarSpeciality.isVisible = false
                        bindData(it.data!!)
                    }
                }
            }
        }
    }

    private fun bindData(data: Speciality) = with(binding) {
        specialityName.text = data.nombre
        specialityDesc.text = data.descripcion
        "$ ${data.precio}".also { specialityPrice.text = it }

        imgSpeciality.loadDrawable(data.url_foto)
    }
}