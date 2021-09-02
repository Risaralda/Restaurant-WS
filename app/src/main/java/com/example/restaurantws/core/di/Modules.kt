package com.example.restaurantws.core.di

import android.content.Context
import androidx.room.Room
import com.example.restaurantws.core.api.ApiService
import com.example.restaurantws.core.database.RestaurantDB
import com.example.restaurantws.data.auth.AuthRepository
import com.example.restaurantws.data.main.MainRepository
import com.example.restaurantws.ui.categories.CategoriesViewModel
import com.example.restaurantws.ui.login.LoginViewModel
import com.example.restaurantws.ui.pedidos.PedidosViewModel
import com.example.restaurantws.ui.products.ProductsViewModel
import com.example.restaurantws.ui.sign_up.SignUpViewModel
import com.example.restaurantws.ui.speciality.SpecialityViewModel
import com.example.restaurantws.ui.splash.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coreModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            RestaurantDB::class.java, "rs-ws"
        ).build()
    }
    single {
        get<RestaurantDB>().pedidosDao()
    }
    single {
        androidContext().getSharedPreferences("prefs-ws", Context.MODE_PRIVATE)
    }
    single {
        ApiService()

    }

}
val authModule = module {
    single {
        AuthRepository(get())
    }

    viewModel {
        LoginViewModel(get())
    }
    viewModel {
        SignUpViewModel(get())
    }

}
val mainModule = module {
    single {
        MainRepository(get(), get())
    }

    viewModel {
        SpecialityViewModel(get())
    }
    viewModel {
        CategoriesViewModel(get())
    }
    viewModel {
        ProductsViewModel(get())
    }
    viewModel {
        PedidosViewModel(get())
    }
    viewModel {
        MainViewModel(get())
    }

}