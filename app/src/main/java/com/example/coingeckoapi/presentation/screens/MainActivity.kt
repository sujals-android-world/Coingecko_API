package com.example.coingeckoapi.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.coingeckoapi.presentation.navigation.RootNavigationGraph
import com.example.coingeckoapi.presentation.ui.theme.CoingeckoAPITheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()
            CoingeckoAPITheme {
                RootNavigationGraph(navHostController)
            }
        }
    }
}
