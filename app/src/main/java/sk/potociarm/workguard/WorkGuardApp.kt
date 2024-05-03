package sk.potociarm.workguard

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import sk.potociarm.workguard.ui.navigation.AppNavHost

/**
 * Top level composable that represents screens for the application.
 */
@Composable
fun WorkGuardApp(navController: NavHostController = rememberNavController()) {
    AppNavHost(navController = navController)
}
