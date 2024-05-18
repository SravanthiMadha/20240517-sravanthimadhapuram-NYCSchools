package com.sra.jpmc.nyc.navigation

import android.content.Intent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.sra.jpmc.nyc.network.model.SchoolItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.sra.jpmc.nyc.ui.details.SchoolDetailsScreenContent
import com.sra.jpmc.nyc.ui.home.SchoolHomeScreenContent
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * HomeNavigationGraph will help to handle navigation Eg. Home To Details
 *
 * @param navController
 * @param innerPadding
 */
@Composable
fun SchoolNavigationGraph(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController,
        startDestination = SchoolRoute.Home.route,
        Modifier.padding(innerPadding)
    ) {
        composable(SchoolRoute.Home.route) {
            SchoolHomeScreenContent { data ->
                val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
                val jsonAdapter = moshi.adapter(SchoolItem::class.java).lenient()
                val selectedSchoolData = jsonAdapter.toJson(data)
                val encodedData =
                    URLEncoder.encode(selectedSchoolData, StandardCharsets.UTF_8.toString())
                navController.navigate(SchoolRoute.SchoolDetails.getRoute(encodedData))
            }
        }

        composable(
            SchoolRoute.SchoolDetails.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "${DeepLink.SCHOOL_DETAILS}${SchoolRoute.SchoolDetails.parameter}"
                    action = Intent.ACTION_VIEW
                }
            ),
            arguments = listOf(
                navArgument(SchoolRoute.SchoolDetails.parameter) {
                    type = NavType.StringType
                }
            )

        ) { backStackEntry ->
            backStackEntry.arguments?.getString(SchoolRoute.SchoolDetails.parameter)?.let { data ->
                val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
                val jsonAdapter = moshi.adapter(SchoolItem::class.java).lenient()
                val decodedData = URLDecoder.decode(data, StandardCharsets.UTF_8.toString())
                val selectedSchool = jsonAdapter.fromJson(decodedData)
                if (selectedSchool != null) {
                    SchoolDetailsScreenContent(selectedSchool)
                }
            }
        }
    }
}
