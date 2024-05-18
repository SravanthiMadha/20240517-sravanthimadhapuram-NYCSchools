package com.sra.jpmc.nyc.di

import com.sra.jpmc.nyc.ui.details.SchoolDetailsViewModel
import com.sra.jpmc.nyc.ui.home.SchoolHomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * DI - All ViewModel will be added in this Module
 */
val viewModelModule = module {
    viewModel { SchoolHomeViewModel(get()) }
    viewModel { SchoolDetailsViewModel(get()) }
}
