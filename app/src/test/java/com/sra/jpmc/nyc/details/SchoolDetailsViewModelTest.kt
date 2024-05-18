package com.sra.jpmc.nyc.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sra.jpmc.nyc.network.APIResult
import com.sra.jpmc.nyc.network.model.SchoolSatItem
import com.sra.jpmc.nyc.network.repository.SchoolsRepository
import com.sra.jpmc.nyc.ui.details.SchoolDetailsViewModel
import com.sra.jpmc.nyc.ui.details.SchoolSatDetailsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class SchoolDetailsViewModelTest {

    private lateinit var schoolDetailsViewModel: SchoolDetailsViewModel
    private lateinit var repository: SchoolsRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        repository = Mockito.mock(SchoolsRepository::class.java)
        schoolDetailsViewModel = SchoolDetailsViewModel((repository))
    }

    @Test
    fun getSchoolSatSuccessTest() = runBlocking {
        val schoolSatDetails = listOf(
            SchoolSatItem(
                "12ABC3",
                school_name = "School 1",
                sat_math_avg_score = "123",
                sat_writing_avg_score = "222",
                sat_critical_reading_avg_score = "300"
            )
        )

        Mockito.`when`(repository.fetchNycSchoolsDetails("12ABC3")).thenReturn(
            APIResult.Success(
                schoolSatDetails
            )
        )

        schoolDetailsViewModel.getSchoolDetails("12ABC3")

        assertEquals(
            schoolSatDetails.size,
            schoolDetailsViewModel.homeDetailsUiState.value?.schoolDetails?.size
        )
    }

    @Test
    fun getSchoolSatErrorTest() = runBlocking {
        Mockito.`when`(repository.fetchNycSchoolsDetails("12ABC3")).thenReturn(
            APIResult.Error(
                responseBodyData = "Api call failed",
                responseCode = 500
            )
        )

        schoolDetailsViewModel.getSchoolDetails("12ABC3")

        assertEquals(SchoolSatDetailsUiState.Error, schoolDetailsViewModel.homeDetailsUiState.value)
        assertEquals(0, schoolDetailsViewModel.homeDetailsUiState.value?.schoolDetails?.size)
    }

}