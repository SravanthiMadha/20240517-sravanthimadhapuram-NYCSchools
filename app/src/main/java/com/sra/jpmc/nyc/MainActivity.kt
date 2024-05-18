package com.sra.jpmc.nyc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sra.jpmc.nyc.common.views.TopBar
import com.sra.jpmc.nyc.navigation.SchoolNavigationGraph
import com.sra.jpmc.nyc.ui.theme.NYCSchoolTheme

/**
 *  MainActivity is the starting point of the applications
 *
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NYCSchoolTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(topBar = {
        TopBar(navController)
    }) { innerPadding ->
        SchoolNavigationGraph(
            navController, innerPadding
        )
    }
}