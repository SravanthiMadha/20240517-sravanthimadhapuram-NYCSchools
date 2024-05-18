package com.sra.jpmc.nyc.di

import com.sra.jpmc.nyc.network.repository.SchoolsRepository
import org.koin.dsl.module

/**
 * DI - All Repository will be added in this Module
 */
val repositoryModule = module {
    single { SchoolsRepository(get()) }
}
