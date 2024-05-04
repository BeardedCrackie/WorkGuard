
package sk.potociarm.workguard.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import sk.potociarm.workguard.ui.home.HomeScreen
import sk.potociarm.workguard.ui.tags.WorkTagDetailsDestination
import sk.potociarm.workguard.ui.tags.WorkTagDetailsScreen
import sk.potociarm.workguard.ui.tags.WorkTagEntryDestination
import sk.potociarm.workguard.ui.tags.WorkTagEntryScreen
import sk.potociarm.workguard.ui.tags.WorkTagListDestination
import sk.potociarm.workguard.ui.tags.WorkTagListScreen


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
            WorkTagListScreen(
                navigateToWorkTagEntry = { navController.navigate(WorkTagEntryDestination.route) },
                navigateToWorkTagUpdate = { navController.navigate("${WorkTagDetailsDestination.route}/${it}")
                },
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(
            route = WorkTagDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(WorkTagDetailsDestination.ID_ARG) {
                type = NavType.IntType
            })
        ) {
            WorkTagDetailsScreen(
                navigateToEditWorkTag = {
                    navController.navigate("${WorkTagDetailsDestination.route}/$it")
                },
                navigateBack = { navController.popBackStack() },
                navigateTParentWorkTag = {
                    navController.navigate("${WorkTagDetailsDestination.route}/$it")
                }
            )
        }
        composable(route = WorkTagEntryDestination.route) {
            WorkTagEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.popBackStack() }
            )
        }
    }
}
