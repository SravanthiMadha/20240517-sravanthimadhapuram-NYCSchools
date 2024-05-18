package com.sra.jpmc.nyc.ui.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sra.jpmc.nyc.R
import com.sra.jpmc.nyc.network.model.SchoolItem
import com.sra.jpmc.nyc.ui.theme.Typography
import com.sra.jpmc.nyc.ui.theme.md_theme_light_primary

/**
 * [SchoolContactsDetailsContent] to display the School detailed information
 * @property SchoolItem
 */
@Composable
fun SchoolContactsDetailsContent(schoolBasicData: SchoolItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 2.dp
    ) {
        val context = LocalContext.current
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 5.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.property_color_icon),
                    contentDescription = stringResource(R.string.contact)
                )
                Spacer(Modifier.width(25.dp))
                Column {
                   MobileNumberContent(context,schoolBasicData)
                   EmailContent(context,schoolBasicData)
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${schoolBasicData.primary_address_line_1}, ${schoolBasicData.city}, ${schoolBasicData.state_code}-${schoolBasicData.zip}".trimEnd(),
                    style = Typography.body1
                )
                if (schoolBasicData.latitude?.isNotEmpty() == true && schoolBasicData.longitude?.isNotEmpty() == true) {
                    NavigationMapCta(
                        schoolBasicData.latitude.toDouble(), schoolBasicData.latitude.toDouble()
                    )
                }
            }
            val websiteUrl = schoolBasicData.website.toString()
            val url = if (!websiteUrl.startsWith("http")) {
                "http://$websiteUrl"
            } else {
                websiteUrl
            }
            Text(text = websiteUrl, style = Typography.subtitle1.copy(
                color = Color.Blue
            ), modifier = Modifier
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }
            )
        }
    }
}

/**
 * To display the Email information with navigation option
 */
@Composable
fun EmailContent(context: Context, schoolBasicData: SchoolItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_email),
            contentDescription = stringResource(
                R.string.contact
            )
        )
        Spacer(Modifier.width(5.dp))
        Text(text = "${schoolBasicData.school_email}", style = TextStyle(
            textDecoration = TextDecoration.Underline,
            fontSize = 16.sp,
            color = md_theme_light_primary
            // Optional: specify other text styles
        ), modifier = Modifier
            .clickable {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data =
                        Uri.parse("mailto:") // This ensures only email apps respond
                    putExtra(
                        Intent.EXTRA_EMAIL,
                        arrayOf("${schoolBasicData.school_email}")
                    ) // Optional: specify email recipient(s)
                }
                context.startActivity(intent)
            }
            .padding(bottom = 2.dp)
        )
    }
}

/**
 * To display the Mobile Number with call option
 */
@Composable
fun MobileNumberContent(context: Context, schoolBasicData: SchoolItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 5.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_call),
            contentDescription = stringResource(R.string.contact)
        )
        Spacer(Modifier.width(5.dp))
        Text(text = "${schoolBasicData.phone_number}", style = TextStyle(
            textDecoration = TextDecoration.Underline,
            fontSize = 16.sp,
            // Optional: specify other text styles
        ), modifier = Modifier
            .clickable {
                val intent = Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel:${schoolBasicData.phone_number}")
                )
                context.startActivity(intent)
            }
            .padding(bottom = 8.dp)
        )
    }}

/**
 * To display the Map icon with navigation
 */
@Composable
fun NavigationMapCta(latitude: Double, longitude: Double) {
    val context = LocalContext.current
    val uri = Uri.parse("geo:$latitude,$longitude")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    Image(painter = painterResource(id = R.drawable.ic_map),
        contentDescription = stringResource(
            R.string.find_in_map
        ),
        modifier = Modifier
            .size(35.dp)
            .padding(start = 5.dp)
            .clickable {
                context.startActivity(intent)
            })
}