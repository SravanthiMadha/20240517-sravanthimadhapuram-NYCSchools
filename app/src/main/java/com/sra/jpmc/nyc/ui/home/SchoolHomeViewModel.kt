package com.sra.jpmc.nyc.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sra.jpmc.nyc.network.model.SchoolItem
import com.sra.jpmc.nyc.network.onError
import com.sra.jpmc.nyc.network.onSuccess
import com.sra.jpmc.nyc.network.repository.SchoolsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel for handling business logic, api calling of School List
 *
 * @property schoolsRepository
 */
class SchoolHomeViewModel(private val schoolsRepository: SchoolsRepository) : ViewModel() {

    var schollsListState: MutableLiveData<SchoolHomeUiState> = MutableLiveData()
    private var _schollsList = MutableStateFlow<List<SchoolItem>>(emptyList())
    var _schollsListValues = _schollsList.asStateFlow()

    //first state whether the search is happening or not
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    //second state the text typed by the user
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()
    private var isLoading = false
    private var offset = 0
    private val limit = 10

    init {
        getSchoolListData()
    }

    @SuppressLint("SuspiciousIndentation")
    fun getSchoolListData() {
        if (isLoading ||offset>600) return
             isLoading = true
        viewModelScope.launch{
            try {
                schollsListState.postValue(SchoolHomeUiState.Loading)
                schoolsRepository.fetchNycSchools(offset).onSuccess {
                    _schollsList.value= _schollsListValues.value+it
                    Log.e("abcd offset", "${offset}")
                    Log.e("abcd it", "${it.size}")
                    Log.e("abcd list", "${_schollsListValues.value.size}")

                        schollsListState.postValue(
                        SchoolHomeUiState.Success(it)
                    )
                    offset += limit

                }.onError {
                    schollsListState.postValue(SchoolHomeUiState.Error)
                    Log.e("response error", "${it.responseBodyData}")
                }
            } catch (e: Exception) {
                isLoading = false
            } finally {
                isLoading = false
            }

    }
}


        var schoolsSearchList: StateFlow<List<SchoolItem>?> = searchText
            .combine(_schollsListValues) { text, homeUiState ->//combine searchText with _contriesList
                if (text.isBlank()) { //return the entery list of countries if not is typed
                    homeUiState
                }
                homeUiState.filter { country ->// filter and return a list of countries based on the text the user typed
                    country.school_name?.uppercase()?.contains(text.trim().uppercase()) ?: false
                }
            }.stateIn( scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),//it will allow the StateFlow survive 5 seconds before it been canceled
                initialValue = _schollsListValues.value
            )


    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onToogleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }
}