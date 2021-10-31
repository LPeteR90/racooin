package com.greyhairredbear.racooin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import arrow.core.right
import com.greyhairredbear.racooin.core.interfaces.ApiClient
import com.greyhairredbear.racooin.core.interfaces.Persistence
import com.greyhairredbear.racooin.core.interfaces.Resource
import com.greyhairredbear.racooin.core.model.CryptoBalance
import com.greyhairredbear.racooin.core.model.CryptoCurrency
import com.greyhairredbear.racooin.core.model.CryptoCurrencyRate
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

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiClient: ApiClient,
    private val persistence: Persistence,
) : ViewModel() {
    private val _uiState = MutableStateFlow<Resource<List<CryptoCurrencyRate>>>(Resource.Loading)
    val uiState: StateFlow<Resource<List<CryptoCurrencyRate>>> = _uiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
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
                ifRight = {},
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
        CryptoBalance(cryptoCurrency = CryptoCurrency.BITCOIN, balance = 0.0),
        CryptoBalance(cryptoCurrency = CryptoCurrency.ETHEREUM, balance = 0.0),
        CryptoBalance(cryptoCurrency = CryptoCurrency.DOGECOIN, balance = 0.0),
        CryptoBalance(cryptoCurrency = CryptoCurrency.RIPPLE, balance = 0.0),
        CryptoBalance(cryptoCurrency = CryptoCurrency.LITECOIN, balance = 0.0),
    ).right()
}
