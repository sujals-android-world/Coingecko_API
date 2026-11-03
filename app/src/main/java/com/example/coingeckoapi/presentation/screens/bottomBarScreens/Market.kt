package com.example.coingeckoapi.presentation.screens.bottomBarScreens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.coingeckoapi.domain.model.CoinModel
import com.example.coingeckoapi.presentation.coinViewModel.CoinState
import com.example.coingeckoapi.presentation.coinViewModel.UiEvent
import kotlinx.coroutines.flow.Flow

@Composable
fun Market(
    coinListState : CoinState,
    searchQuery : String,
    eventFlow: Flow<UiEvent>,
    onSearchQueryChanged: (String) -> Unit,
    onItemClick: (String) -> Unit,
    onAddSwipe : (String) -> Unit,
    preferenceEventFlow : Flow<UiEvent>

) {
    val context = LocalContext.current

    LaunchedEffect(eventFlow) {
        eventFlow.collect { event ->
            when (event) {
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(preferenceEventFlow) {
        preferenceEventFlow.collect { event ->
            when(event) {
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(35.dp))


            OutlinedTextField(
                value = searchQuery,
                onValueChange = { query ->
                    onSearchQueryChanged(query)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                placeholder = {
                    Text(text = "Search crypto (e.g. Bitcoin)", color = MaterialTheme.colorScheme.onSurfaceVariant)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    coinListState.isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    coinListState.isError.isNotBlank() -> {
                        Text(
                            text = coinListState.isError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    coinListState.coinList.isEmpty() -> {
                        Text(
                            text = "No crypto found 🪙",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = paddingBottomSpacer()
                        ) {
                            items(coinListState.coinList,key = {it.id}) { coin ->

                                SwipeableCoinCard(
                                    coinModel = coin,
                                    onItemClick = onItemClick,
                                    onSwipeAction = onAddSwipe
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableCoinCard(
    coinModel: CoinModel,
    onItemClick: (String) -> Unit,
    onSwipeAction: (String) -> Unit,
    swipeText: String = "Add",
    swipeBackgroundColor: Color = Color(0xFF2E7D32),
    swipeDirection: SwipeToDismissBoxValue = SwipeToDismissBoxValue.StartToEnd
) {
    val swipeState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == swipeDirection) {
                onSwipeAction(coinModel.id)
            }
            false
        }
    )

    val enableStartToEnd = swipeDirection == SwipeToDismissBoxValue.StartToEnd
    val enableEndToStart = swipeDirection == SwipeToDismissBoxValue.EndToStart

    SwipeToDismissBox(
        state = swipeState,
        enableDismissFromStartToEnd = enableStartToEnd,
        enableDismissFromEndToStart = enableEndToStart,
        backgroundContent = {
            val alignment = if (swipeDirection == SwipeToDismissBoxValue.StartToEnd) {
                Alignment.CenterStart
            } else {
                Alignment.CenterEnd
            }

            val paddingModifier = if (swipeDirection == SwipeToDismissBoxValue.StartToEnd) {
                Modifier.padding(start = 24.dp)
            } else {
                Modifier.padding(end = 24.dp)
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(swipeBackgroundColor),
                contentAlignment = alignment
            ) {
                Text(
                    text = swipeText,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = paddingModifier
                )
            }
        }
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onItemClick(coinModel.id)
                },
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = coinModel.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = coinModel.symbol.uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Text(
                            text = "Rank #${coinModel.market_cap_rank}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "₹${coinModel.current_price}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    val isPositive = coinModel.price_change_percentage_24h >= 0
                    val priceColor = if (isPositive) Color(0xFF2E7D32) else Color(0xFFC62828)

                    Text(
                        text = "${if (isPositive) "+" else ""}${String.format("%.2f", coinModel.price_change_percentage_24h)}%",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = priceColor
                    )
                }
            }
        }
    }
}
@Composable
fun paddingBottomSpacer(): PaddingValues {
    return PaddingValues(bottom = 24.dp)
}