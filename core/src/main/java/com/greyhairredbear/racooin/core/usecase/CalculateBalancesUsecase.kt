package com.greyhairredbear.racooin.core.usecase

import arrow.core.Either
import arrow.core.computations.either
import com.greyhairredbear.racooin.core.businesslogic.withFiatBalances
import com.greyhairredbear.racooin.core.model.CryptoBalance
import com.greyhairredbear.racooin.core.model.CryptoCurrencyRate
import com.greyhairredbear.racooin.core.model.CryptoFiatBalance
import com.greyhairredbear.racooin.core.model.UsecaseError

suspend fun calculateBalances(
    fetchCurrencyRates: suspend () -> Either<UsecaseError, List<CryptoCurrencyRate>>,
    fetchCryptoBalances: suspend () -> Either<UsecaseError, List<CryptoBalance>>,
): Either<UsecaseError, List<CryptoFiatBalance>> =
    either {
        val rates = fetchCurrencyRates().bind()
        val cryptoBalances = fetchCryptoBalances().bind()
        val result = cryptoBalances.withFiatBalances(rates).bind()
        result
    }
