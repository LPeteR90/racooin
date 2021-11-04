package com.greyhairredbear.racooin.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.greyhairredbear.racooin.core.model.CryptoBalance
import com.greyhairredbear.racooin.core.model.CryptoCurrency
import com.greyhairredbear.racooin.core.model.FiatCurrency

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

@Preview
@Composable
fun CryptoCurrencyOverviewListElement(
    @PreviewParameter(CryptoCurrencyOverviewUiModelProvider::class) uiState: CryptoCurrencyOverviewUiModel,
// TODO: put into data class for this composable together with uiState to enable prieview
//    onUpdatedBalance: (CryptoBalance) -> Unit,
) {
    Column {
        Row {
            Text(text = uiState.cryptoCurrency.name)
        }
        Row {
            TextField(
                value = uiState.cryptoBalance.toString(),
                onValueChange = {},
            )
        }
        Row {
            Text(text = "Balance")
            Text(text = uiState.fiatBalance.toString() + uiState.fiatCurrency.name)
        }
        Row {
            Text(text = "Rate")
            Text(text = uiState.rate.toString() + uiState.fiatCurrency.name)
        }
    }
}

class UpdatedBalanceProvider : PreviewParameterProvider<(CryptoBalance) -> Unit> {
    override val values = sequenceOf<(CryptoBalance) -> Unit>({})

}

class CryptoCurrencyOverviewUiModelProvider :
    PreviewParameterProvider<CryptoCurrencyOverviewUiModel> {
    override val values = sequenceOf(
        CryptoCurrencyOverviewUiModel(
            CryptoCurrency.ETHEREUM,
            FiatCurrency.EURO,
            rate = 2452.86,
            cryptoBalance = 1.02,
            fiatBalance = 2720.08
        )
    )

}
