package sk.potociarm.workguard

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import sk.potociarm.workguard.ui.navigation.AppNavHost
import java.text.NumberFormat

const val TIMEOUT_MILLIS = 5_000L
val RATE_SYMBOL = NumberFormat.getNumberInstance().currency?.symbol ?: "€"
val HOUR_RATE_SYMBOL = "$RATE_SYMBOL/h"


/**
 * Top level composable that represents screens for the application.
 */
@Composable
fun WorkGuardApp(navController: NavHostController = rememberNavController()) {
    AppNavHost(navController = navController)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkGuardTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
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
            }
        },
        actions = actions
    )
}