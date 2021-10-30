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
import com.greyhairredbear.racooin.core.interfaces.Resource
import com.greyhairredbear.racooin.core.model.CryptoFiatBalance

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    SwipeRefresh(
        state = rememberSwipeRefreshState(
            true // TODO: viewmodel.isRefreshing
        ),
        onRefresh = { viewModel.refresh() }
    ) {

    }
}

// TODO Preview
@Composable
fun CryptoCurrencySummary(
    uiState: Resource<List<CryptoFiatBalance>>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(rememberScrollState()),
    ) {
        when (uiState) {
            is Resource.Success -> {
                (uiState as Resource.Success).data.forEach {
                    Text(
                        text = "1 ${it.cryptoBalance.cryptoCurrency.name} = " +
                                "${it.fiatBalance.balance} ${it.fiatBalance.fiatCurrency}"
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
