package com.sra.jpmc.nyc.common.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sra.jpmc.nyc.R
import com.sra.jpmc.nyc.navigation.SchoolRoute
import com.sra.jpmc.nyc.ui.theme.Typography


/**
 * TopBar for activity
 *
 * @param navController
 */
@Composable
fun TopBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = navBackStackEntry?.destination
    val onRefreshClick = {
        navigateAndClearBackstack(
            SchoolRoute.Home.route
        , navController
        )
    }
    when (currentScreen?.route) {
        SchoolRoute.Home.route -> {
            TitleBar(stringResource(SchoolRoute.Home.title), false,onRefreshClick)
        }

        SchoolRoute.SchoolDetails.route -> {
            TitleBar(stringResource(SchoolRoute.SchoolDetails.title), true, onBackClick = {
                if (navController.previousBackStackEntry != null) {
                    navController.navigateUp()
                }
            } )
        }
    }
}

fun navigateAndClearBackstack(route: String, navController: NavHostController) {
    val navOptions = NavOptions.Builder()
        .setPopUpTo(route, true)
        .build()
    navController.navigate(route, navOptions)
}
/**
 * TitleBar/Action bar with dynamic configuration like title , back button visibility
 *
 * @param title
 * @param showBack
 * @param onBackClick
 */
@Composable
fun TitleBar(title: String, showBack: Boolean,  onRefreshClick: (() -> Unit)? = null,onBackClick: (() -> Unit)? = null,) {
    TopAppBar {
        Box(modifier = Modifier.fillMaxWidth()) {
            if (showBack) {
                IconButton(
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = { onBackClick?.invoke() }
                ) {
                    Image(
                        painterResource(id = R.drawable.ic_back),
                        contentDescription = stringResource(id = R.string.content_desc_back_icon)
                    )
                }
            }else{
                IconButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = { onRefreshClick?.invoke() }
                ) {
                    Image(
                        painterResource(id = R.drawable.ic_refresh),
                        contentDescription = "Refresh"
                    )
                }
            }

            Text(
                text = title,
                style = Typography.h5,
                modifier = Modifier.align(Alignment.Center)
            )

        }
    }
}