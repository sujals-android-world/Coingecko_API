package com.example.coingeckoapi.presentation.navigation.bottomNavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.coingeckoapi.presentation.navigation.Routes

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Market : BottomBarScreen(Routes.MARKET, "Market", Icons.Default.Home)
    object Watchlist : BottomBarScreen(Routes.WATCHLIST, "Watchlist", Icons.Default.Star)
    object Portfolio : BottomBarScreen(Routes.PORTFOLIO, "Portfolio", Icons.Default.AccountBalanceWallet)
    object Settings : BottomBarScreen(Routes.SETTINGS, "Settings", Icons.Default.Settings)
}

val bottomScreens = listOf(
    BottomBarScreen.Market,
    BottomBarScreen.Portfolio,
    BottomBarScreen.Watchlist,
    BottomBarScreen.Settings
)