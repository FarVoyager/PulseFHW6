package com.example.pulsefhw6

import androidx.navigation.NavController
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DiModule {
    fun getViewModelModule() = module {
        viewModel {
            MainViewModel()
        }
    }
}