package com.example.coingeckoapi.presentation.navigation.bottomNavigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.coingeckoapi.presentation.coinViewModel.MarketViewModel
import com.example.coingeckoapi.presentation.coinViewModel.PortfolioViewModel
import com.example.coingeckoapi.presentation.coinViewModel.PreferenceViewModel
import com.example.coingeckoapi.presentation.coinViewModel.UserViewModel
import com.example.coingeckoapi.presentation.navigation.Routes
import com.example.coingeckoapi.presentation.screens.bottomBarScreens.Market
import com.example.coingeckoapi.presentation.screens.bottomBarScreens.PortFolioScreen
import com.example.coingeckoapi.presentation.screens.bottomBarScreens.Setting
import com.example.coingeckoapi.presentation.screens.bottomBarScreens.WatchListScreen

@Composable
fun MainScreen(rootNavController: NavHostController) {

    val navHostController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController = navHostController) }
    ) { padding ->
        NavHost(
            navController = navHostController,
            startDestination = Routes.MARKET,
            modifier = Modifier.padding(padding)
        ) {
            composable(Routes.MARKET) {
                val marketViewModel: MarketViewModel = hiltViewModel()
                val getAllCoinState by marketViewModel.getAllCoin.collectAsStateWithLifecycle()
                val searchQuery by marketViewModel.searchQuery.collectAsStateWithLifecycle()
                Market(
                    coinListState = getAllCoinState,
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { marketViewModel.onSearchQueryChanged(it) },
                    eventFlow = marketViewModel.eventFlow,
                    onItemClick = { coinId ->
                        rootNavController.navigate("coinDetail/$coinId")
                    }
                )
            }
            composable(Routes.PORTFOLIO) {
                val viewModel : PreferenceViewModel = hiltViewModel()
                val isLoggedIn = viewModel.isUserAvailable.collectAsStateWithLifecycle().value
                val portfolioViewModel : PortfolioViewModel = hiltViewModel()
                val userDetailState = portfolioViewModel.userDetail.collectAsStateWithLifecycle()
                PortFolioScreen(
                    onNavigateToAuth = {
                        rootNavController.navigate(Routes.CREATE_USER) {
                            launchSingleTop = true
                        }
                    },
                    isLoggedIn = isLoggedIn,
                    userState = userDetailState.value,
                    onLogout = {  }
                )
            }

            composable(Routes.WATCHLIST) {
                WatchListScreen()
            }
            composable(Routes.SETTINGS) {
                Setting()
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        bottomScreens.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) }
            )
        }
    }
}