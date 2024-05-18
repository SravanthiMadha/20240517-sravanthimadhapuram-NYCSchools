package com.sra.jpmc.nyc.navigation

import androidx.annotation.StringRes
import com.sra.jpmc.nyc.R

/**
 * School Path routes are defined here
 *
 * @property route
 * @property title
 */
sealed class SchoolRoute(
    val route: String,
    @StringRes val title: Int = 0,
) {
    object Home :
        SchoolRoute(route = "home", title = R.string.app_name)

    object SchoolDetails :
        SchoolRoute(route = "schoolDetails/{data}", title = R.string.details_title) {
        const val parameter = "data"
        fun getRoute(data: String) = "schoolDetails/$data"
    }
}

