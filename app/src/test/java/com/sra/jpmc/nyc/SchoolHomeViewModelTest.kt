package com.sra.jpmc.nyc

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sra.jpmc.nyc.network.APIResult
import com.sra.jpmc.nyc.network.model.SchoolItem
import com.sra.jpmc.nyc.network.repository.SchoolsRepository
import com.sra.jpmc.nyc.ui.home.SchoolHomeUiState
import com.sra.jpmc.nyc.ui.home.SchoolHomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class SchoolHomeViewModelTest {

    private lateinit var schoolHomeViewModel: SchoolHomeViewModel
    private lateinit var repository: SchoolsRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        repository = mock(SchoolsRepository::class.java)
        schoolHomeViewModel = SchoolHomeViewModel((repository))
    }
    /*Test schoolHomeViewModel while school response from API*/

    @Test
    fun getSchoolListSuccessTest() = runBlocking {
        val schoolList = listOf(
            SchoolItem("12ABC3", school_name = "School 1"),
            SchoolItem("12XYZ3", school_name = "School 2")
        )

        `when`(repository.fetchNycSchools(0)).thenReturn(
            APIResult.Success(
                schoolList
            )
        )

        schoolHomeViewModel.getSchoolListData()

        assertEquals(schoolList.size, schoolHomeViewModel._schollsListValues.value.size)
    }
    /*Test schoolHomeViewModel while error response from API*/
    @Test
    fun getSchoolListErrorTest() = runBlocking {

        `when`(repository.fetchNycSchools(0)).thenReturn(
            APIResult.Error(
                responseBodyData = "Api call failed",
                responseCode = 500
            )
        )

        schoolHomeViewModel.getSchoolListData()

        assertEquals(SchoolHomeUiState.Error, schoolHomeViewModel.schollsListState.value)
        assertEquals(0, schoolHomeViewModel.schoolsSearchList.value?.size)
    }


}