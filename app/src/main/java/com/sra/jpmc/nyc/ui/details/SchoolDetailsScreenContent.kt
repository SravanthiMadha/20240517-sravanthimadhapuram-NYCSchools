package com.sra.jpmc.nyc.ui.details

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sra.jpmc.nyc.R
import com.sra.jpmc.nyc.common.isInternetAvailable
import com.sra.jpmc.nyc.common.views.AppProgressBar
import com.sra.jpmc.nyc.common.views.ErrorScreen
import com.sra.jpmc.nyc.network.model.SchoolItem
import com.sra.jpmc.nyc.ui.theme.Typography
import com.sra.jpmc.nyc.ui.theme.md_theme_light_primary
import org.koin.androidx.compose.getViewModel

/**
 * Composable to display all Schools Details
 *
 * @param schoolBasicData
 */
@Composable
fun SchoolDetailsScreenContent(schoolBasicData: SchoolItem) {
    val schoolDetailsViewModel = getViewModel<SchoolDetailsViewModel>()
    val homeDetailsUiState by schoolDetailsViewModel.homeDetailsUiState.observeAsState(
        SchoolSatDetailsUiState.Loading
    )
    val context = LocalContext.current

    when (homeDetailsUiState) {
        SchoolSatDetailsUiState.Error -> {
            ErrorScreen {
                if (isInternetAvailable(context))
                    schoolDetailsViewModel.getSchoolDetails(schoolBasicData.dbn.toString())
                else
                    Toast.makeText(
                        context,
                        context.getText(R.string.internet_connection_warning),
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
        SchoolSatDetailsUiState.Loading -> {
            AppProgressBar()
        }
        is SchoolSatDetailsUiState.Success -> {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = schoolBasicData.school_name.toString(),
                    style = Typography.h5,
                    color = md_theme_light_primary,
                    modifier = Modifier.padding(5.dp)
                )
                SchoolContactsDetailsContent(schoolBasicData = schoolBasicData)
                SchoolSatDetailsContent(schoolSatDetails = homeDetailsUiState.schoolDetails)
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                SchoolGeneralDetailsContent(schoolBasicData = schoolBasicData)
            }
        }
    }

    LaunchedEffect(Unit) {
        schoolDetailsViewModel.getSchoolDetails(schoolBasicData.dbn.toString())
    }
}
