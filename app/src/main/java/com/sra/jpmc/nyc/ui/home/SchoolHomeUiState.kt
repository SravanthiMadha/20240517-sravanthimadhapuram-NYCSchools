package com.sra.jpmc.nyc.ui.home

import com.sra.jpmc.nyc.network.model.SchoolItem

/**
 * Class to handle School List Screen different states
 *
 * @property schools
 */
sealed class SchoolHomeUiState(
    var schools: List<SchoolItem> = listOf()
) {
    object Loading : SchoolHomeUiState()
    object Error : SchoolHomeUiState()
    class Success(schools: List<SchoolItem>) : SchoolHomeUiState(
        schools = schools
    )

}
