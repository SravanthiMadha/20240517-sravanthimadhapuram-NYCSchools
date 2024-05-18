package com.sra.jpmc.nyc.network.repository

import com.sra.jpmc.nyc.network.model.SchoolItem
import com.sra.jpmc.nyc.network.model.SchoolSatItem
import com.sra.jpmc.nyc.network.APIResult
import com.sra.jpmc.nyc.network.safeApiCall
import com.sra.jpmc.nyc.network.service.NycSchoolService

/**
 * Repository to handle NYC school API call
 *
 * @property nycSchoolService
 */
class SchoolsRepository(private val nycSchoolService: NycSchoolService) {

    suspend fun fetchNycSchools(offset: Int): APIResult<List<SchoolItem>> {
        return safeApiCall {
            nycSchoolService.fetchSchools(offset=offset)
        }
    }

    suspend fun fetchNycSchoolsDetails(dbn: String): APIResult<List<SchoolSatItem>> {
        return safeApiCall {
            nycSchoolService.fetchSchoolDetails(dbn)
        }
    }
}