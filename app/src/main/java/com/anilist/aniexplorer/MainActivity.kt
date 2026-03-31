package com.anilist.aniexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anilist.aniexplorer.ui.details.DetailsScreen
import com.anilist.aniexplorer.ui.home.HomeScreen
import com.anilist.aniexplorer.ui.theme.AnilistExplorerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnilistExplorerTheme {
                val navController = rememberNavController()
                
                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        HomeScreen(
                            onNavigateToDetails = { animeId ->
                                navController.navigate("details/$animeId")
                            }
                        )
                    }
                    composable(
                        route = "details/{animeId}",
                        arguments = listOf(
                            navArgument("animeId") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val animeId = backStackEntry.arguments?.getInt("animeId") ?: 0
                        DetailsScreen(
                            animeId = animeId,
                            onBackClick = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnilistExplorerTheme {
        Greeting("Android")
    }
}