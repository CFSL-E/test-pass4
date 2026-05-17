package com.passkeeper.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                    val density = androidx.compose.ui.platform.LocalDensity.current
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        enterTransition = {
                            androidx.compose.animation.fadeIn(androidx.compose.animation.core.tween(300)) +
                                androidx.compose.animation.slideInHorizontally(androidx.compose.animation.core.tween(300)) {
                                    with(density) { 30.dp.roundToPx() }
                                }
                        },
                        exitTransition = {
                            androidx.compose.animation.fadeOut(androidx.compose.animation.core.tween(300)) +
                                androidx.compose.animation.slideOutHorizontally(androidx.compose.animation.core.tween(300)) {
                                    with(density) { -30.dp.roundToPx() }
                                }
                        },
                        popEnterTransition = {
                            androidx.compose.animation.fadeIn(androidx.compose.animation.core.tween(300)) +
                                androidx.compose.animation.slideInHorizontally(androidx.compose.animation.core.tween(300)) {
                                    with(density) { -30.dp.roundToPx() }
                                }
                        },
                        popExitTransition = {
                            androidx.compose.animation.fadeOut(androidx.compose.animation.core.tween(300)) +
                                androidx.compose.animation.slideOutHorizontally(androidx.compose.animation.core.tween(300)) {
                                    with(density) { 30.dp.roundToPx() }
                                }
                        }
                    ) {
                        composable("home") {
                            HomeScreen(
                                onNavigateToSettings = { navController.navigate("settings") },
                                onNavigateToAdd = { navController.navigate("add") },
                                onNavigateToDetail = { id -> navController.navigate("detail/$id") }
                            )
                        }
                        composable("settings") {
                            SettingsScreen(onNavigateBack = { navController.popBackStack() })
                        }
                        composable(
                            route = "add",
                            enterTransition = {
                                androidx.compose.animation.scaleIn(initialScale = 0.8f, animationSpec = androidx.compose.animation.core.tween(300)) +
                                    androidx.compose.animation.fadeIn(animationSpec = androidx.compose.animation.core.tween(300))
                            },
                            exitTransition = {
                                androidx.compose.animation.scaleOut(targetScale = 0.8f, animationSpec = androidx.compose.animation.core.tween(300)) +
                                    androidx.compose.animation.fadeOut(animationSpec = androidx.compose.animation.core.tween(300))
                            }
                        ) {
                            androidx.compose.material3.Scaffold(
                                modifier = Modifier.fillMaxSize(),
                                containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface
                            ) { innerPadding ->
                                androidx.compose.foundation.layout.Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding)
                                        .androidx.compose.foundation.clickable { navController.popBackStack() },
                                    contentAlignment = androidx.compose.ui.Alignment.Center
                                ) {
                                    androidx.compose.material3.Text("添加页面（点击返回）")
                                }
                            }
                        }
                        composable(
                            route = "detail/{id}",
                            enterTransition = {
                                androidx.compose.animation.scaleIn(initialScale = 0.8f, animationSpec = androidx.compose.animation.core.tween(300)) +
                                    androidx.compose.animation.fadeIn(animationSpec = androidx.compose.animation.core.tween(300))
                            },
                            exitTransition = {
                                androidx.compose.animation.scaleOut(targetScale = 0.8f, animationSpec = androidx.compose.animation.core.tween(300)) +
                                    androidx.compose.animation.fadeOut(animationSpec = androidx.compose.animation.core.tween(300))
                            }
                        ) { backStackEntry ->
                            androidx.compose.material3.Scaffold(
                                modifier = Modifier.fillMaxSize(),
                                containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface
                            ) { innerPadding ->
                                androidx.compose.foundation.layout.Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding)
                                        .androidx.compose.foundation.clickable { navController.popBackStack() },
                                    contentAlignment = androidx.compose.ui.Alignment.Center
                                ) {
                                    androidx.compose.material3.Text("详情页面 ID: ${backStackEntry.arguments?.getString("id")}（点击返回）")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
