package sk.potociarm.workguard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import sk.potociarm.workguard.ui.events.WorkEventListDestination
import sk.potociarm.workguard.ui.navigation.AppNavHost
import sk.potociarm.workguard.ui.tags.WorkTagListDestination
import java.text.NumberFormat

const val TIMEOUT_MILLIS = 5_000L
val RATE_SYMBOL = NumberFormat.getNumberInstance().currency?.symbol ?: "€"
val HOUR_RATE_SYMBOL = "$RATE_SYMBOL/h"


/**
 * Top level composable that represents screens for the application.
 */
@Composable
fun WorkGuardApp(navController: NavHostController = rememberNavController()) {
    val drawerState = rememberDrawerState(DrawerValue.Open)
    val scope = rememberCoroutineScope()
    val selectedScreen = remember { mutableStateOf(navController.currentDestination?.route) }

    navController.addOnDestinationChangedListener { _, destination, _ ->
        selectedScreen.value = destination.route!!
    }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = DrawerDefaults.shape,
            ) {
                Column(
                    modifier = Modifier.padding(all = dimensionResource(id = R.dimen.padding_large))
                ) {
                    Text(
                        stringResource(id = R.string.app_name),
                        modifier = Modifier.padding(16.dp),
                    )
                    HorizontalDivider()

                    NavigationDrawerItem(
                        //icon = { Icons.Rounded.Edit },
                        icon = { Icon(Icons.Rounded.Edit, contentDescription = "") },
                        label = { Text(text = "Tags") },
                        selected = selectedScreen.value == WorkTagListDestination.route,
                        onClick = {
                            selectedScreen.value = WorkTagListDestination.route
                            scope.launch { drawerState.close() }
                            navController.navigate(WorkTagListDestination.route)
                        },
                    )
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Filled.DateRange, contentDescription = null) },
                        label = { Text(text = "Events") },
                        selected = selectedScreen.value == WorkEventListDestination.route,
                        onClick = {
                            selectedScreen.value = WorkEventListDestination.route
                            scope.launch { drawerState.close() }
                            navController.navigate(WorkEventListDestination.route)
                        },
                    )
                }
            }
        },
        modifier = Modifier.statusBarsPadding(),
        drawerState = drawerState,
        gesturesEnabled = true,
        scrimColor = Color.Black.copy(alpha = 0.3f)
    ) {
        AppNavHost(
            navController = navController,
            openMenu = { scope.launch { drawerState.open() }}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkGuardTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    openNavigation: () -> Unit = {},
    actions: @Composable (RowScope.() -> Unit) = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            } else {
                IconButton(onClick = openNavigation) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.List,
                        contentDescription = stringResource(R.string.menu_home)
                    )
                }
            }
        },
        actions = actions
    )
}
