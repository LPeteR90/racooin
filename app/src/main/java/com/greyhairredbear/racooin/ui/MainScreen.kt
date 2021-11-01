package com.greyhairredbear.racooin.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.greyhairredbear.racooin.core.model.CryptoFiatBalance

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    SwipeRefresh(
        state = rememberSwipeRefreshState(
            false // TODO: viewmodel.isRefreshing
        ),
        onRefresh = { viewModel.refresh() }
    ) {
        CryptoCurrencySummary(uiState)
    }
}

// TODO Preview
@Composable
fun CryptoCurrencySummary(
    uiState: Resource<List<CryptoCurrencyOverviewUiModel>>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(rememberScrollState()),
    ) {
        when (uiState) {
            is Resource.Success -> {
                uiState.data.forEach {
                    Text(
                        text = "${it.cryptoBalance} ${it.cryptoCurrency.name} = " +
                                "${it.fiatBalance} ${it.fiatCurrency.name}"
                    )
                }
            }
            is Resource.Error -> {
                Text(text = "Error")
            }
            is Resource.Loading -> {
                Text(text = "Loading")
            }
        }
    }
}
