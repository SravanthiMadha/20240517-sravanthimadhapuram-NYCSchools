package com.sra.jpmc.nyc.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sra.jpmc.nyc.R
import com.sra.jpmc.nyc.network.model.SchoolSatItem
import com.sra.jpmc.nyc.ui.theme.Typography
import com.sra.jpmc.nyc.ui.theme.md_theme_light_primary

/**
 * Composable to display Sat Details of school
 * i.e  math score,writing score, reading score
 */
@Composable
fun SchoolSatDetailsContent(
    schoolSatDetails: List<SchoolSatItem>
) {
    if (schoolSatDetails.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val schoolSat = schoolSatDetails[0]
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.sat),
                    style = Typography.h6,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = "SAT test takers - ${ schoolSat.num_of_sat_test_takers.toString()}",
                    style = Typography.body1,
                )
            }


            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                ScoreBox(
                    label = stringResource(R.string.math),
                    score = schoolSat.sat_math_avg_score.toString(),
                    color = md_theme_light_primary,
                    modifier = Modifier.padding(end = 4.dp)
                )
                ScoreBox(
                    label = stringResource(R.string.writing),
                    score = schoolSat.sat_writing_avg_score.toString(),
                    color = md_theme_light_primary,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                ScoreBox(
                    label = stringResource(R.string.reading),
                    score = schoolSat.sat_critical_reading_avg_score.toString(),
                    color = md_theme_light_primary,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

        }
    }
}

@Composable
fun ScoreBox(
    label: String,
    score: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = Typography.caption,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Box(
            modifier = Modifier
                .height(56.dp)
                .width(56.dp)
                .background(color, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = score,
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
        }
    }
}