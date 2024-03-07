package io.github.trucksmonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import io.github.trucksmonitor.ui.theme.TrucksMonitorTheme
import io.github.trucksmonitor.vehicle.NavGraphs

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TrucksMonitorTheme {
                val navController = rememberNavController()

                Scaffold {
                    DestinationsNavHost(
                        navController = navController,
                        navGraph = NavGraphs.root,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .padding(it)
                            .fillMaxSize(),
                    )
                }
            }
        }
    }
}