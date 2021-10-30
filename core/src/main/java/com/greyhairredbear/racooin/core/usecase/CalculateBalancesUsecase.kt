package com.greyhairredbear.racooin.core.usecase

import arrow.core.Either
import arrow.core.computations.either
import com.greyhairredbear.racooin.core.businesslogic.toFiatBalances
import com.greyhairredbear.racooin.core.interfaces.ApiClient
import com.greyhairredbear.racooin.core.interfaces.Persistence
import com.greyhairredbear.racooin.core.model.CryptoBalance
import com.greyhairredbear.racooin.core.model.CryptoCurrencyRate
import com.greyhairredbear.racooin.core.model.FiatBalance
import com.greyhairredbear.racooin.core.model.UsecaseError
import java.time.LocalDateTime

suspend fun calculateBalances(
    fetchCurrencyRates: suspend () -> Either<UsecaseError, List<CryptoCurrencyRate>>,
    fetchCryptoBalances: suspend () -> Either<UsecaseError, List<CryptoBalance>>,
): Either<UsecaseError, List<FiatBalance>> =
    either {
        val rates = fetchCurrencyRates().bind()
        val cryptoBalances = fetchCryptoBalances().bind()
        val result = cryptoBalances.toFiatBalances(rates).bind()
        result
    }
