package com.sra.jpmc.nyc.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sra.jpmc.nyc.R

val robotFontFamily = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_bold, FontWeight.Bold)
)
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = robotFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h5 = TextStyle(
        fontFamily = robotFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    h6 = TextStyle(
        fontFamily = robotFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    caption = TextStyle(
        fontFamily = robotFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    subtitle1 = TextStyle(
        fontFamily = robotFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    )
)