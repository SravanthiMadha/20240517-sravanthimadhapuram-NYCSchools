package com.sra.jpmc.nyc.di

import com.sra.jpmc.nyc.network.service.NycSchoolService
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * DI - All API services will be added in this Module
 */
val servicesModule = module {
    single {
        provideService<NycSchoolService>(
            get()
        )
    }
}

/**
 * Returns the API interface for the provided service type
 */
private inline fun <reified T> provideService(retrofit: Retrofit) = retrofit.create(T::class.java)
