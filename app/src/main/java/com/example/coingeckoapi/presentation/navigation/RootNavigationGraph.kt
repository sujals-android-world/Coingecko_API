package com.example.coingeckoapi.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.coingeckoapi.presentation.coinViewModel.CreateAccountViewModel
import com.example.coingeckoapi.presentation.coinViewModel.DetailCoinViewmodel
import com.example.coingeckoapi.presentation.coinViewModel.PreferenceViewModel
import com.example.coingeckoapi.presentation.navigation.bottomNavigation.MainScreen
import com.example.coingeckoapi.presentation.screens.DetailedCoin
import com.example.coingeckoapi.presentation.screens.CreateUserScreen

@Composable
fun RootNavigationGraph(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = Routes.MAIN) {

        composable(Routes.MAIN) {
            MainScreen(rootNavController = navHostController)
        }

        composable(Routes.COIN_DETAIL) {
            val detailCoinViewmodel : DetailCoinViewmodel = hiltViewModel()
            val detailState by detailCoinViewmodel.coinDetailState.collectAsStateWithLifecycle()
            DetailedCoin(state = detailState)
        }

        composable(Routes.CREATE_USER){
            val viewmodel : CreateAccountViewModel = hiltViewModel()
            val preferenceViewmodel : PreferenceViewModel = hiltViewModel()
            val state = viewmodel.createAccountResult.collectAsStateWithLifecycle().value

            CreateUserScreen(
                result = state,
                onCreateUser = { userModel ->
                    viewmodel.createUser(userModel)
                },
                onUserCreated = {
                    preferenceViewmodel.saveUser(true)
                    navHostController.navigate(Routes.MAIN) {
                        popUpTo(Routes.CREATE_USER) {
                            inclusive = true
                        }
                    }
                }
            )
        }

    }
}