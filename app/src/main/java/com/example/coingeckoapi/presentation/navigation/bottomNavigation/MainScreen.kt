package com.example.coingeckoapi.presentation.navigation.bottomNavigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.coingeckoapi.presentation.coinViewModel.CoinState
import com.example.coingeckoapi.presentation.coinViewModel.MarketViewModel
import com.example.coingeckoapi.presentation.coinViewModel.PortfolioViewModel
import com.example.coingeckoapi.presentation.coinViewModel.PreferenceViewModel
import com.example.coingeckoapi.presentation.coinViewModel.WatchlistViewmodel
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
            startDestination = Routes.PORTFOLIO,
            modifier = Modifier.padding(padding)
        ) {

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
                    onProfileClick = { rootNavController.navigate(Routes.PROFILE_SCREEN) }
                )
            }
            composable(Routes.MARKET) {
                val marketViewModel: MarketViewModel = hiltViewModel()
                val userPreferenceViewModel : PreferenceViewModel = hiltViewModel()
                val getAllCoinState by marketViewModel.getAllCoin.collectAsStateWithLifecycle()
                val searchQuery by marketViewModel.searchQuery.collectAsStateWithLifecycle()
                Market(
                    coinListState = getAllCoinState,
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { marketViewModel.onSearchQueryChanged(it) },
                    eventFlow = marketViewModel.eventFlow,
                    onItemClick = { coinId ->
                        rootNavController.navigate("coinDetail/$coinId")
                    },
                    onAddSwipe = { id ->
                        userPreferenceViewModel.addToWatchlist(id)
                    },
                    preferenceEventFlow = userPreferenceViewModel.events
                )
            }
            composable(Routes.WATCHLIST) {
                val watchlistViewmodel : WatchlistViewmodel = hiltViewModel()
                WatchListScreen(
                    watchList = watchlistViewmodel.watchlistCoins.collectAsStateWithLifecycle().value,
                    removeCard = { id ->
                        watchlistViewmodel.removeFromWatchList(id)
                    }
                )
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

    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets,
        modifier = Modifier.height(90.dp),
        containerColor = Color.White
    ) {
        bottomScreens.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                ),
                icon = {
                    Icon(
                        screen.icon,
                        contentDescription = screen.title,
                        modifier = Modifier
                            .size(22.dp)
                            .offset(y = 4.dp)
                    )
                },
                label = {
                    Text(
                        text = screen.title,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                        modifier = Modifier.offset(y = (-2).dp)
                    )
                }
            )
        }
    }
}