package com.sra.jpmc.nyc.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SchoolItem(
    val dbn: String? = null,
    val school_email: String? = null,
    val school_name: String? = null,
    val overview_paragraph: String? = null,
    val website: String? = null,
    val extracurricular_activities: String? = null,
    val primary_address_line_1: String? = null,
    val phone_number: String? = null,
    val school_sports: String? = null,
    val total_students: String? = null,
    val city: String? = null,
    val state_code: String? = null,
    val zip: String? = null,
    val latitude: String? = null,
    val longitude: String? = null,
)