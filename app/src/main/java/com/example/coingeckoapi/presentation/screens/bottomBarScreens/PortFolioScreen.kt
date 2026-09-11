package com.example.coingeckoapi.presentation.screens.bottomBarScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.coingeckoapi.presentation.coinViewModel.UserDetailState

@Composable
fun PortFolioScreen(
    isLoggedIn: Boolean,
    userState: UserDetailState,
    onNavigateToAuth: () -> Unit,
    onLogout: () -> Unit
) {
    if (isLoggedIn) {
        UserScreen(userState = userState, onLogout = onLogout)
    } else {
        ShowEmptyProfileScreen(onNavigateToAuth)
    }
}

@Composable
fun UserScreen(
    userState : UserDetailState,
    onLogout: () -> Unit
) {
    if(userState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            userState.user?.let { user ->
                Text(text = "Welcome, ${user.name}")
                Text(text = user.email, modifier = Modifier.padding(top = 8.dp))
                Button(onClick = onLogout, modifier = Modifier.padding(top = 24.dp)) {
                    Text(text = "Logout")
                }
            }
        }
    }
}

@Composable
fun ShowEmptyProfileScreen(onNavigateToAuth: () -> Unit) {

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text("Create an Account to Use This Feature")
            Button(onClick = onNavigateToAuth, modifier = Modifier.padding(top = 16.dp)) {
                Text("Sign Up")
            }
        }
    }
}