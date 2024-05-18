package com.sra.jpmc.nyc.ui.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sra.jpmc.nyc.network.onError
import com.sra.jpmc.nyc.network.onSuccess
import com.sra.jpmc.nyc.network.repository.SchoolsRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for handling business logic, api calling of School Details
 *
 * @property schoolsRepository
 */
class SchoolDetailsViewModel(
    private val schoolsRepository: SchoolsRepository
) :
    ViewModel() {
    var homeDetailsUiState: MutableLiveData<SchoolSatDetailsUiState> = MutableLiveData()
        private set

    fun getSchoolDetails(dbn: String) = viewModelScope.launch {
        homeDetailsUiState.postValue(SchoolSatDetailsUiState.Loading)

        schoolsRepository.fetchNycSchoolsDetails(dbn).onSuccess {
            homeDetailsUiState.postValue(
                SchoolSatDetailsUiState.Success(it)
            )
        }.onError {
            homeDetailsUiState.postValue(SchoolSatDetailsUiState.Error)
            Log.e("response error", "${it.responseBodyData}")
        }
    }
}