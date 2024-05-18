package com.sra.jpmc.nyc.ui.details

import com.sra.jpmc.nyc.network.model.SchoolSatItem

/**
 * Class to handle School Sat Details Screen different states
 *
 * @property schoolDetails
 */
sealed class SchoolSatDetailsUiState(
    var schoolDetails: List<SchoolSatItem> = listOf()
) {
    object Loading : SchoolSatDetailsUiState()
    object Error : SchoolSatDetailsUiState()
    class Success(schools: List<SchoolSatItem>) : SchoolSatDetailsUiState(
        schoolDetails = schools
    )
}
