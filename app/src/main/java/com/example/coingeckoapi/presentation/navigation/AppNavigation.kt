package com.example.coingeckoapi.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.coingeckoapi.presentation.coinViewModel.CoinViewModel
import com.example.coingeckoapi.presentation.screens.CoinScreen
import com.example.coingeckoapi.presentation.screens.DetailedCoin

@Composable
fun AppNavigation(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = Routes.COIN_LIST) {
        composable(Routes.COIN_LIST) {
            val coinViewModel: CoinViewModel = hiltViewModel()
            val getAllCoinState by coinViewModel.getAllCoin.collectAsStateWithLifecycle()
            val searchQuery by coinViewModel.searchQuery.collectAsStateWithLifecycle()

            CoinScreen(
                coinListState = getAllCoinState,
                searchQuery = searchQuery,
                onSearchQueryChanged = { coinViewModel.onSearchQueryChanged(it) },
                eventFlow = coinViewModel.eventFlow,
                onItemClick = { coinId ->
                    navHostController.navigate("coinDetail/$coinId")
                }
            )
        }
        composable(Routes.COIN_DETAIL) { backStackEntry ->
            val coinId = backStackEntry.arguments?.getString("coinId")
            val coinViewModel: CoinViewModel = hiltViewModel()
            val detailState by coinViewModel.coinDetailState.collectAsStateWithLifecycle()

            DetailedCoin(state = detailState)
        }
    }
}