
package sk.potociarm.workguard.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.coroutines.Job
import sk.potociarm.workguard.ui.events.WorkEventDetailsDestination
import sk.potociarm.workguard.ui.events.WorkEventDetailsScreen
import sk.potociarm.workguard.ui.events.WorkEventEditDestination
import sk.potociarm.workguard.ui.events.WorkEventEditScreen
import sk.potociarm.workguard.ui.events.WorkEventEntryDestination
import sk.potociarm.workguard.ui.events.WorkEventEntryScreen
import sk.potociarm.workguard.ui.events.WorkEventListDestination
import sk.potociarm.workguard.ui.events.WorkEventListScreen
import sk.potociarm.workguard.ui.home.HomeScreen
import sk.potociarm.workguard.ui.tags.WorkTagDetailsDestination
import sk.potociarm.workguard.ui.tags.WorkTagDetailsScreen
import sk.potociarm.workguard.ui.tags.WorkTagEditDestination
import sk.potociarm.workguard.ui.tags.WorkTagEditScreen
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
    openMenu: () -> Job,
) {
    NavHost(
        //navController = navController, startDestination = HomeScreen.route, modifier = modifier
        navController = navController, startDestination = WorkEventListDestination.route, modifier = modifier
    ) {

        // ----- Home screen -----
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

        // ----- Work Tag List screen -----
        composable(route = WorkTagListDestination.route) {
            WorkTagListScreen(
                navigateToWorkTagEntry = { navController.navigate(WorkTagEntryDestination.route) },
                navigateToWorkTagUpdate = { navController.navigate("${WorkTagDetailsDestination.route}/${it}")
                },
                openNavigation = { openMenu() }
            )
        }

        // ----- Work Tag Detail screen -----
        composable(
            route = WorkTagDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(WorkTagDetailsDestination.ID_ARG) {
                type = NavType.IntType
            })
        ) {
            WorkTagDetailsScreen(
                navigateToEditWorkTag = {
                    navController.navigate("${WorkTagEditDestination.route}/$it")
                },
                navigateBack = {
                    navController.popBackStack(WorkTagListDestination.route, false)
                },
                navigateToParentWorkTag = {
                    navController.navigate("${WorkTagDetailsDestination.route}/$it")
                }
            )
        }

        // ----- Work Tag Entry screen -----
        composable(route = WorkTagEntryDestination.route) {
            WorkTagEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.popBackStack() }
            )
        }

        // ----- Work Tag Edit screen -----
        composable(
            route = WorkTagEditDestination.routeWithArgs,
            arguments = listOf(navArgument(WorkTagEditDestination.ID_ARG) {
                type = NavType.IntType
            })) {
            WorkTagEditScreen(
                navigateBack = { navController.popBackStack() },
                onDelete = { navController.popBackStack(WorkTagListDestination.route, false) },
            )
        }

        // ----- Work Event List screen -----
        composable(route = WorkEventListDestination.route) {
            WorkEventListScreen(
                navigateToWorkEventEntry = { navController.navigate(WorkEventEntryDestination.route) },
                navigateToWorkEventUpdate = { navController.navigate("${WorkEventDetailsDestination.route}/$it") },
                openNavigation = { openMenu() }
            )
        }

        // ----- Work Event Entry screen -----
        composable(route = WorkEventEntryDestination.route) {
            WorkEventEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.popBackStack() }
            )
        }

        // ----- Work Event Detail screen -----
        composable(
            route = WorkEventDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(WorkEventDetailsDestination.ID_ARG) {
                type = NavType.IntType
            })
        ) {
            WorkEventDetailsScreen(
                navigateToWorkTag = {
                    navController.navigate("${WorkTagDetailsDestination.route}/$it")
                },
                navigateBack = {
                    navController.popBackStack(WorkTagListDestination.route, false)
                },
                navigateToEditWorkEvent = {
                    navController.navigate("${WorkEventEditDestination.route}/$it")
                }
            )
        }

        // ----- Work Event Edit screen -----
        composable(
            route = WorkEventEditDestination.routeWithArgs,
            arguments = listOf(navArgument(WorkEventEditDestination.ID_ARG) {
                type = NavType.IntType
            })) {
            WorkEventEditScreen(
                navigateBack = { navController.popBackStack() },
                onDelete = { navController.popBackStack(WorkEventListDestination.route, false) },
                onNavigateUp = { navController.popBackStack() }
            )
        }
    }
}
