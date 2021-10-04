package com.greyhairredbear.racooin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import arrow.core.computations.either
import com.greyhairredbear.racooin.apiclient.CoingeckoApiClient
import com.greyhairredbear.racooin.core.ApiClient
import com.greyhairredbear.racooin.core.ApiClientError
import com.greyhairredbear.racooin.core.model.CurrencyRate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }

        lifecycleScope.launchWhenResumed {
            withContext(Dispatchers.IO) {
                val ignored = ""
                println(ignored)
            }
        }
    }
}

@Composable
private fun MainScreen(
    screenViewModel: MainViewModel = viewModel()
) {
    val uiState by screenViewModel.uiState.collectAsState()
    when (uiState) {
        is Resource.Success -> {
            Text(text = "Success")
        }
        is Resource.Error -> {
            Text(text = "Error")
        }
        is Resource.Loading -> {
            Text(text = "Loading")
        }
    }
}

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<Resource<List<CurrencyRate>>>(Resource.Loading)
    val uiState: StateFlow<Resource<List<CurrencyRate>>> = _uiState
    private val apiClient: ApiClient = CoingeckoApiClient() // TODO: DI

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val test = either<ApiClientError, List<CurrencyRate>> {
                val currencyRateResult = apiClient.fetchCurrencyRates().bind()
                currencyRateResult
            }

            test.fold(
                ifRight = { _uiState.value = Resource.Success(it) },
                ifLeft = { _uiState.value = Resource.Error("failed api call") },
            )
        }
    }
}

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}
