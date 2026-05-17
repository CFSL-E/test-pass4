package com.passkeeper.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.passkeeper.app.ui.screens.HomeScreen
import com.passkeeper.app.ui.screens.SettingsScreen
import com.passkeeper.app.ui.theme.PassKeeperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PassKeeperTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(onNavigateToSettings = { navController.navigate("settings") })
                        }
                        composable(
                            route = "settings",
                            enterTransition = {
                                slideIntoContainer(
                                    towards = androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = androidx.compose.animation.core.tween(300)
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    towards = androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Right,
                                    animationSpec = androidx.compose.animation.core.tween(300)
                                )
                            },
                            popEnterTransition = {
                                slideIntoContainer(
                                    towards = androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Right,
                                    animationSpec = androidx.compose.animation.core.tween(300)
                                )
                            },
                            popExitTransition = {
                                slideOutOfContainer(
                                    towards = androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Right,
                                    animationSpec = androidx.compose.animation.core.tween(300)
                                )
                            }
                        ) {
                            SettingsScreen(onNavigateBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}
