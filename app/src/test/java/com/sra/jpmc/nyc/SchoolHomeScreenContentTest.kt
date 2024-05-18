package com.sra.jpmc.nyc

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.sra.jpmc.nyc.network.APIResult
import com.sra.jpmc.nyc.network.model.SchoolItem
import com.sra.jpmc.nyc.network.repository.SchoolsRepository
import com.sra.jpmc.nyc.ui.home.SchoolHomeScreenContent
import com.sra.jpmc.nyc.ui.home.SchoolHomeUiState
import com.sra.jpmc.nyc.ui.home.SchoolHomeViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

 class SchoolHomeScreenContentTest {
    private lateinit var schoolHomeViewModel: SchoolHomeViewModel
    private lateinit var repository: SchoolsRepository
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        repository = Mockito.mock(SchoolsRepository::class.java)
        schoolHomeViewModel = SchoolHomeViewModel((repository))
    }
    @Test
     fun schoolList_Displayedd() {

    }
    @Test
    suspend fun schoolList_Displayed() {
        // Given
        val schools = listOf(
            SchoolItem("School 1", "New York", "12345"),
            SchoolItem("School 2", "New York", "23456")
        )
        // When
        composeTestRule.setContent {
            SchoolHomeScreenContent(onListItemClick = {})
        }

        // Then
        composeTestRule.onNodeWithText("School 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("School 2").assertIsDisplayed()
    }

    @Test
     suspend fun getSchoolListErrorTest() {
        Mockito.`when`(repository.fetchNycSchools(10)).thenReturn(
            APIResult.Error(
                responseBodyData = "Api call failed",
                responseCode = 500
            )        )

        schoolHomeViewModel.getSchoolListData()

        assertEquals(SchoolHomeUiState.Error, schoolHomeViewModel.schollsListState.value)
        Assert.assertEquals(0, schoolHomeViewModel.schollsListState.value?.schools?.size)
        // When
        composeTestRule.setContent {
            SchoolHomeScreenContent(onListItemClick = {})
        }

        // Then
        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
    }
}