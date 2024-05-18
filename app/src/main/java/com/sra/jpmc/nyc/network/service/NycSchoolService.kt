package com.sra.jpmc.nyc.network.service

import com.sra.jpmc.nyc.BuildConfig
import com.sra.jpmc.nyc.network.model.SchoolItem
import com.sra.jpmc.nyc.network.model.SchoolSatItem
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * All NYC API Services
 *
 */
interface NycSchoolService {

    @Headers("X-App-Token: ${BuildConfig.api_token}")
    @GET("resource/s3k6-pzi2.json")
    suspend fun fetchSchools(
        @Query("\$limit") limit: Int = 10,
        @Query("\$offset") offset: Int,
    ): List<SchoolItem>
    @Headers("X-App-Token: ${BuildConfig.api_token}")
    @GET("resource/f9bf-2cp4.json")
    suspend fun fetchSchoolDetails(
        @Query("dbn") dbn: String
    ): List<SchoolSatItem>
}
