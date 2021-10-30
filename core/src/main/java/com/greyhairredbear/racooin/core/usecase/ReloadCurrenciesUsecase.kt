package com.greyhairredbear.racooin.core.usecase

import arrow.core.Either
import arrow.core.computations.either
import com.greyhairredbear.racooin.core.interfaces.ApiClient
import com.greyhairredbear.racooin.core.interfaces.Persistence
import com.greyhairredbear.racooin.core.model.ApiClientError
import com.greyhairredbear.racooin.core.model.CryptoCurrencyRate
import com.greyhairredbear.racooin.core.model.PersistenceError
import com.greyhairredbear.racooin.core.model.UsecaseError

suspend fun reloadCurrencyRates(
    fetchRatesFromApi: suspend () -> Either<ApiClientError, List<CryptoCurrencyRate>>,
    persistRates: suspend (List<CryptoCurrencyRate>) -> Either<PersistenceError, Unit>,
): Either<UsecaseError, List<CryptoCurrencyRate>> = either {
    val rates = fetchRatesFromApi().bind()
    persistRates(rates).bind()
    rates
}
