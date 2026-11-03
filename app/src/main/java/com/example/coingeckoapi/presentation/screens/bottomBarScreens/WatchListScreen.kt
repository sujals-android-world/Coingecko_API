package com.example.coingeckoapi.presentation.screens.bottomBarScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.coingeckoapi.domain.model.CoinModel
import kotlinx.coroutines.flow.Flow

@Composable
fun WatchListScreen(
    watchList : List<CoinModel>,
    removeCard : (id : String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = paddingBottomSpacer()
    ) {
        items(watchList,key = {it.id}) { coin ->
            SwipeableCoinCard(
                coinModel = coin,
                onItemClick = {},
                onSwipeAction = removeCard,
                swipeText = "Remove",
                swipeBackgroundColor = Color.Red,
                swipeDirection = SwipeToDismissBoxValue.EndToStart
            )
        }
    }
}