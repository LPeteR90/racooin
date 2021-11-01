package com.greyhairredbear.racooin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import arrow.core.right
import com.greyhairredbear.racooin.core.businesslogic.toCurrencyRate
import com.greyhairredbear.racooin.core.interfaces.ApiClient
import com.greyhairredbear.racooin.core.interfaces.Persistence
import com.greyhairredbear.racooin.core.model.CryptoBalance
import com.greyhairredbear.racooin.core.model.CryptoCurrency
import com.greyhairredbear.racooin.core.model.FiatCurrency
import com.greyhairredbear.racooin.core.model.UsecaseError
import com.greyhairredbear.racooin.core.usecase.calculateBalances
import com.greyhairredbear.racooin.core.usecase.getCurrencyRates
import com.greyhairredbear.racooin.core.usecase.reloadCurrencyRates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

data class CryptoCurrencyOverviewUiModel(
    val cryptoCurrency: CryptoCurrency,
    val fiatCurrency: FiatCurrency,
    val rate: Double,
    val cryptoBalance: Double,
    val fiatBalance: Double,
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiClient: ApiClient,
    private val persistence: Persistence,
) : ViewModel() {
    // TODO
    //  ui model
    //  - crypto currency name
    //  - current rate in EUR
    //  - balance (editable)
    //  - balance in EUR
    //  Nice 2 have
    //  - invest (editable)
    //  - development in percent

    // TODO
    //  merge all data into single flow with own ui model (CryptoCurrencyOverviewUiModel)

    private val _uiState =
        MutableStateFlow<Resource<List<CryptoCurrencyOverviewUiModel>>>(Resource.Loading)
    val uiState: StateFlow<Resource<List<CryptoCurrencyOverviewUiModel>>> = _uiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            // TODO: load crypto balances from persistence and fill UI text inputs
            // TODO: call getCurrencyRates separately to fill UI with rate accordingly

            // TODO refactor duplicate code (all but fetchCurrencyRates param equal to refresh call)
            calculateBalances(
                fetchCurrencyRates = {
                    getCurrencyRates(
                        fetchRatesFromDb = { persistence.fetchCurrencyRates() },
                        refreshThreshold = LocalDateTime.now().minusDays(1),
                        fetchRatesFromApi = { apiClient.fetchCurrencyRates() },
                        persistRates = { persistence.persistCurrencyRates(it) }
                    )
                },
                // TODO: for starters, use hardcoded list of own balances
//                fetchCryptoBalances = { persistence.fetchAllCryptoBalances() },
                fetchCryptoBalances = ::dummyCryptoBalances,
            ).fold(
                ifRight = { balances ->
                    _uiState.value = Resource.Success(
                        balances.map {
                            CryptoCurrencyOverviewUiModel(
                                cryptoCurrency = it.cryptoBalance.cryptoCurrency,
                                fiatCurrency = it.fiatBalance.fiatCurrency,
                                rate = it.toCurrencyRate().fiatBalance.balance,
                                cryptoBalance = it.cryptoBalance.balance,
                                fiatBalance = it.fiatBalance.balance,
                            )
                        }
                    )
                },
                ifLeft = {},
            )
        }
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            calculateBalances(
                fetchCurrencyRates = {
                    reloadCurrencyRates(
                        fetchRatesFromApi = { apiClient.fetchCurrencyRates() },
                        persistRates = { persistence.persistCurrencyRates(it) },
                    )
                },
//                fetchCryptoBalances = { persistence.fetchAllCryptoBalances() },
                fetchCryptoBalances = ::dummyCryptoBalances,
            ).fold(
                ifRight = {},
                ifLeft = {},
            )
        }
    }

    fun persistCryptoBalance(balance: Double, currency: CryptoCurrency) {
        // TODO
    }
}

private fun dummyCryptoBalances(): Either<UsecaseError, List<CryptoBalance>> {
    return listOf(
        CryptoBalance(cryptoCurrency = CryptoCurrency.BITCOIN, balance = 0.27632501),
        CryptoBalance(cryptoCurrency = CryptoCurrency.ETHEREUM, balance = 1.00342883),
        CryptoBalance(cryptoCurrency = CryptoCurrency.DOGECOIN, balance = 1960.2),
        CryptoBalance(cryptoCurrency = CryptoCurrency.RIPPLE, balance = 0.0),
        CryptoBalance(cryptoCurrency = CryptoCurrency.LITECOIN, balance = 0.0),
    ).right()
}
