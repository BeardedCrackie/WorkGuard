
package sk.potociarm.workguard.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import sk.potociarm.workguard.ui.home.HomeScreen
import sk.potociarm.workguard.ui.tags.WorkTagDetailsDestination
import sk.potociarm.workguard.ui.tags.WorkTagDetailsScreen
import sk.potociarm.workguard.ui.tags.WorkTagListDestination


/**
 * Application navigation graph.
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        //navController = navController, startDestination = HomeScreen.route, modifier = modifier
        navController = navController, startDestination = WorkTagListDestination.route, modifier = modifier
    ) {
        composable(route = HomeScreen.route) {
            HomeScreen(
                navigateToItemEntry = {
                    //todo navigation
                     },
                navigateToItemUpdate = {
                    //todo navigation
                }
            )
        }
        composable(route = WorkTagListDestination.route) {
            WorkTagDetailsScreen(
                navigateBack = { navController.popBackStack() },
                //todo navigation
            )
        }
        composable(route = WorkTagDetailsDestination.route) {
            WorkTagDetailsScreen(
                navigateBack = { navController.popBackStack() },
                //todo navigation
            )
        }
    }
}
