package com.sra.jpmc.nyc.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SchoolSatItem(
    val dbn: String? = null,
    val num_of_sat_test_takers: String? = null,
    val sat_critical_reading_avg_score: String? = null,
    val sat_math_avg_score: String? = null,
    val sat_writing_avg_score: String? = null,
    val school_name: String? = null
)