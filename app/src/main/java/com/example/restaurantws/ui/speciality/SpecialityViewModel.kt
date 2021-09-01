package com.example.restaurantws.ui.speciality

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
import com.example.restaurantws.data.main.MainRepository
import com.example.restaurantws.data.main.models.especialidad.Speciality
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SpecialityViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val specialityResult = MutableStateFlow<Resource<Speciality>>(Resource.Empty())
    val signUpResult: StateFlow<Resource<Speciality>> = specialityResult

    fun loadSpeciality() {
        viewModelScope.launch {
            mainRepository.getSpeciality()
                .onStart { specialityResult.value = Resource.Loading() }
                .catch { specialityResult.value = Resource.Error(it) }
                .collect { specialityResult.value = Resource.Success(it) }
        }
    }
}