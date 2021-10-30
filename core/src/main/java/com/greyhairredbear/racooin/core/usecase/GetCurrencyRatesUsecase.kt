package com.greyhairredbear.racooin.core.usecase

import arrow.core.Either
import arrow.core.computations.either
import com.greyhairredbear.racooin.core.interfaces.CryptoCurrencyRatesWithTimestamp
import com.greyhairredbear.racooin.core.model.ApiClientError
import com.greyhairredbear.racooin.core.model.CryptoCurrencyRate
import com.greyhairredbear.racooin.core.model.PersistenceError
import com.greyhairredbear.racooin.core.model.UsecaseError
import java.time.LocalDateTime

suspend fun getCurrencyRates(
    fetchRatesFromDb: suspend () -> Either<PersistenceError, CryptoCurrencyRatesWithTimestamp>,
    refreshThreshold: LocalDateTime,
    fetchRatesFromApi: suspend () -> Either<ApiClientError, List<CryptoCurrencyRate>>,
    persistRates: suspend (List<CryptoCurrencyRate>) -> Either<PersistenceError, Unit>,
): Either<UsecaseError, List<CryptoCurrencyRate>> = either {
    val result = fetchRatesFromDb().bind()
    if (result.timeOfFetching < refreshThreshold) {
        reloadCurrencyRates(fetchRatesFromApi, persistRates).bind()
    } else {
        result.rates
    }
}
