package com.sra.jpmc.nyc.di

import com.sra.jpmc.nyc.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val NETWORK_TIMEOUT = 20L

/**
 * DI - This module helps to handle all network related configuration
 */
val networkModule = module {

    single { provideMoshi() }

    single { provideMoshiConverterFactory(get()) }

    single { provideLoggingInterceptor() }

    factory {
        provideRetrofitClient(
            BuildConfig.base_url,
            get(),
            get()
        )
    }

    single {
        provideHttpClient(
            loggingInterceptor = get()
        )
    }

}

/**
 * Returns [HttpLoggingInterceptor] for [OkHttpClient]
 */
private fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
    level = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.BODY
    } else {
        HttpLoggingInterceptor.Level.NONE
    }
}

/**
 * Returns [OkHttpClient] for [Retrofit]
 *
 * @param loggingInterceptor
 */
private fun provideHttpClient(
    loggingInterceptor: HttpLoggingInterceptor
) = OkHttpClient.Builder()
    .readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
    .addInterceptor(loggingInterceptor)
    .apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(loggingInterceptor)
        }
    }.build()

private fun provideMoshi() =
    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private fun provideRetrofitClient(
    baseUrl: String,
    okHttpClient: OkHttpClient,
    moshiConverterFactory: MoshiConverterFactory
): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(moshiConverterFactory)
        .client(okHttpClient)
        .build()

/**
 * Returns [MoshiConverterFactory] for provided Moshi Instance
 */
private fun provideMoshiConverterFactory(moshi: Moshi) = MoshiConverterFactory.create(moshi)
