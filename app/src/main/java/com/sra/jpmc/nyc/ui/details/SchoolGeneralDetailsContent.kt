package com.sra.jpmc.nyc.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sra.jpmc.nyc.network.model.SchoolItem
import com.sra.jpmc.nyc.ui.theme.Typography

/**
 * Composable to display General Details of school
 * like description
 */
@Composable
fun SchoolGeneralDetailsContent(
    schoolBasicData: SchoolItem,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = schoolBasicData.overview_paragraph.toString(),
            style = Typography.body1,
            modifier = Modifier.padding(bottom = 8.dp),
            lineHeight = 20.sp
        )
    }
}
