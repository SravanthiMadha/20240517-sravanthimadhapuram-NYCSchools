package com.sra.jpmc.nyc.ui.home

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sra.jpmc.nyc.R
import com.sra.jpmc.nyc.common.isInternetAvailable
import com.sra.jpmc.nyc.common.views.AppProgressBar
import com.sra.jpmc.nyc.common.views.ErrorScreen
import com.sra.jpmc.nyc.common.views.SearchBarInputField
import com.sra.jpmc.nyc.network.model.SchoolItem
import com.sra.jpmc.nyc.ui.theme.Typography
import com.sra.jpmc.nyc.ui.theme.md_theme_light_primary
import org.koin.androidx.compose.getViewModel


/**
 * [SchoolHomeScreenContent] to display the list of schools with serch option
 */
@Composable
fun SchoolHomeScreenContent(onListItemClick: ((SchoolItem)) -> Unit) {
    val homeViewModel = getViewModel<SchoolHomeViewModel>()
    val homeUiState by homeViewModel.schollsListState.observeAsState(SchoolHomeUiState.Loading)
    val context = LocalContext.current
    val scrollState = rememberLazyListState()
    var isLoading by remember { mutableStateOf(false) }

    when (homeUiState) {
        SchoolHomeUiState.Error -> {
            ErrorScreen {
                if (isInternetAvailable(context))
                    homeViewModel.getSchoolListData()
                else
                    Toast.makeText(
                        context,
                        context.getText(R.string.internet_connection_warning),
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
        SchoolHomeUiState.Loading -> {
            AppProgressBar()
        }
        is SchoolHomeUiState.Success -> {
            isLoading = false
        }
    }
    Column {
        val searchText by homeViewModel.searchText.collectAsState()
        val isSearching by homeViewModel.isSearching.collectAsState()
        val filteredSchools by homeViewModel.schoolsSearchList.collectAsState()
        Column(
            modifier = Modifier
                .background(md_theme_light_primary)
                .padding(16.dp)
        ) {
            SearchBarInputField(
                query = searchText,//text showed on SearchBar
                onQueryChange = homeViewModel::onSearchTextChange, //update the value of searchText
                onSearch = homeViewModel::onSearchTextChange, //the callback to be invoked when the input service triggers the ImeAction.Search action
                active = isSearching, //whether the user is searching or not
                onActiveChange = { homeViewModel.onToogleSearch() }, //the callback to be invoked when this search bar's active state is changed
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        LazyColumn(
            state = scrollState,
        ) {
            filteredSchools?.let {
                items(it.size) { index ->
                    SchoolItem(filteredSchools!![index], onListItemClick)
                }
                if ((!isLoading) && (searchText.isNullOrBlank()) && scrollState.firstVisibleItemIndex + scrollState.layoutInfo.visibleItemsInfo.size >= filteredSchools!!.size - 2) {
                    isLoading = true
                    homeViewModel.getSchoolListData()
                }
            }


        }
    }
}

@Composable
fun SchoolItem(school: SchoolItem, onListItemClick: (SchoolItem) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 2.dp
    ) {
        val context = LocalContext.current
        Column(
            modifier = Modifier
                .clickable(onClick = {
                    if (isInternetAvailable(context))
                        onListItemClick.invoke(school)
                    else
                        Toast
                            .makeText(
                                context,
                                context.getText(R.string.internet_connection_warning),
                                Toast.LENGTH_SHORT
                            )
                            .show()
                })
                .padding(16.dp)
        ) {
            Text(
                text = school.school_name.toString(),
                style = Typography.h6,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = stringResource(
                        R.string.address
                    )
                )
                Spacer(Modifier.width(2.dp))
                Text(
                    text = "${school.city.toString()},",
                    style = Typography.body1
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = school.zip.toString(),
                    style = Typography.body1,
                    fontStyle = FontStyle.Italic
                )
                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_forward),
                    contentDescription = "next arrow",
                    // Provide a content description for accessibility

                )

            }
            Spacer(Modifier.height(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_call),
                    contentDescription = stringResource(
                        R.string.contact
                    )
                )
                Spacer(Modifier.width(5.dp))
                Text(text = "${school.phone_number}", style = TextStyle(
                    textDecoration = TextDecoration.Underline,
                    fontSize = 16.sp,
                    color = md_theme_light_primary
                    // Optional: specify other text styles
                ), modifier = Modifier
                    .clickable {
                        val intent =
                            Intent(Intent.ACTION_DIAL, Uri.parse("tel:${school.phone_number}"))
                        context.startActivity(intent)
                    }
                    .padding(bottom = 8.dp)
                )
            }

        }
    }
}
