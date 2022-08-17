package com.example.anythingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.anythingapp.ui.theme.AnythingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnythingAppTheme {
                val navController = rememberNavController()
                Scaffold (
                    topBar = { TopBar()},
                    bottomBar = { BottomNavigation(navController = navController)}
                ) {
                    Navigation(navController = navController)
                }
            }
        }
    }
}

fun Color.Companion.parse(colorString: String): Color =
    Color(color = android.graphics.Color.parseColor(colorString))

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Home.route
    ) {
        composable(NavigationItem.Home.route) {
            HomeScreen()
        }
        composable(NavigationItem.Music.route) {
            MusicScreen()
        }
        composable(NavigationItem.Movie.route) {
            MovieScreen()
        }
        composable(NavigationItem.Tip.route) {
            Tip()
        }
        composable(NavigationItem.Profile.route) {
            Portfolio()
        }
    }
}

@Composable
fun A() {

}

@Composable
fun TopBar() {
    TopAppBar(
        contentColor = Color.parse("#333333"),
        backgroundColor = colorResource(id = R.color.colorPrimary),
        title = { Text(
            text = "Anything app",
            fontWeight = FontWeight.Bold
        )},
    )
}

@Composable
fun BottomNavigation(navController: NavHostController) {
    var items = listOf(
        NavigationItem.Home,
        NavigationItem.Music,
        NavigationItem.Movie,
        NavigationItem.Tip,
        NavigationItem.Profile
    )
    androidx.compose.material.BottomNavigation(
        backgroundColor = colorResource(id = R.color.colorPrimary),
        contentColor = Color.parse("#333333")
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute =  navBackStackEntry ?. destination ?. route
        items.forEach {
                item -> BottomNavigationItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute ?. let {
                                    route -> popUpTo(route) {
                                saveState = true
                            }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(painter = painterResource(
                            id = item.icon),
                            contentDescription = item.title)
                    },
                    label = {
                        Text(text = item.title)
                    },
                    selectedContentColor = Color.parse("#333333"),
                    unselectedContentColor = Color.parse("#333333").copy(0.4f),
                    alwaysShowLabel = true
                )
        }
    }
}