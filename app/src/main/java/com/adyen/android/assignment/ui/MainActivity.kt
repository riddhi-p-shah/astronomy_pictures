package com.adyen.android.assignment.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adyen.android.assignment.R
import com.adyen.android.assignment.api.ApiState
import com.adyen.android.assignment.ui.screens.DetailScreen
import com.adyen.android.assignment.ui.screens.ErrorScreen
import com.adyen.android.assignment.ui.screens.ListScreen
import com.adyen.android.assignment.utils.Constants
import com.adyen.android.assignment.utils.Utils
import com.adyen.android.assignment.viewmodels.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.riddhi.weatherforecast.ui.theme.APODTheme

class MainActivity : ComponentActivity() {

    private val mainViewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //for controlling navigation
            val navController = rememberNavController()
            APODTheme {
                Scaffold(
                    floatingActionButton = {
                        val apodApiState = mainViewModel.apodApiState.collectAsState()

                        // Show the reorder FAB only when data is successfully loaded
                        when (apodApiState.value) {
                            is ApiState.Success<*> -> {
                                MyFloatingActionButton()
                            }

                            else -> {}
                        }
                    }
                ) { innerPadding ->
                    APODApp(modifier = Modifier.padding(innerPadding), navController)
                }
            }
        }
    }

    @Composable
    fun APODApp(modifier: Modifier = Modifier, navController: NavHostController) {

        //Used Jetpack navigation to navigate between screens
        NavHost(
            navController = navController, startDestination = Constants.APOD_LIST_SCREEN,
        ) {
            //for list screen
            composable(route = Constants.APOD_LIST_SCREEN) {
                ListScreen(
                    modifier = modifier,
                    mainViewModel = mainViewModel,
                    onSelect = { astronomyPicture ->
                        mainViewModel.selectPicture(astronomyPicture)
                        navController.navigate(Constants.APOD_DETAIL_SCREEN)
                    })
            }
            //for detail screen
            composable(route = Constants.APOD_DETAIL_SCREEN)
            {
                val selectedPicture = mainViewModel.selectedPicture.collectAsState()
                selectedPicture.value?.let {
                    DetailScreen(
                        astronomyPicture = it,
                        onBack = {
                            mainViewModel.resetSelectedPicture()
                            navController.popBackStack() },
                        onFavourite = { astronomyPicture ->
                            Utils.showUpcomingFeatureToast(this@MainActivity)
                            mainViewModel.addFavourite(astronomyPicture)
                        })
                }
            }
        }
    }

    //FAB for reorder feature
    @Composable
    fun MyFloatingActionButton(modifier: Modifier = Modifier) {
        FloatingActionButton(onClick = {
            //As of now, due to time limit could not complete the dialog to display the UI for the same..
            // but sorting logic is done in the viewmodel.
            Toast.makeText(this@MainActivity, "Feature coming soon...", Toast.LENGTH_SHORT).show()
        }) {
            Row(modifier = modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_reorder),
                    contentDescription = "Reorder",
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Reorder List",
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}



