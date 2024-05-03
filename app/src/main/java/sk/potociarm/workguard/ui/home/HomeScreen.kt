package sk.potociarm.workguard.ui.home


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import sk.potociarm.workguard.R
import sk.potociarm.workguard.ui.navigation.NavDestination

object HomeScreen : NavDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToItemUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            //todo TopAppBar()
        },
        bottomBar = {
            //todo BottomAppBar()
        },
        floatingActionButton = {
            //todo FloatingActionButton()
        },
    ) {
    innerPadding -> HomeBody(
        contentPadding = innerPadding,
        modifier = modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun HomeBody(
    modifier: Modifier = Modifier,
    //todo params
    contentPadding: PaddingValues = PaddingValues(0.dp),
    ) {
    Column(
        modifier = modifier
        ) {
        PrintStats()
        PrintRunning()
    }
}

@Composable
fun PrintRunning() {
    //TODO("Not yet implemented")
}

@Composable
fun PrintStats() {
    //TODO("Not yet implemented")
    //https://github.com/PhilJay/MPAndroidChart?tab=readme-ov-file
}
