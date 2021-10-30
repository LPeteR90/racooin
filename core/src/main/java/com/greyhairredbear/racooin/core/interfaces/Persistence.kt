package com.greyhairredbear.racooin.core.interfaces

import arrow.core.Either
import com.greyhairredbear.racooin.core.model.CryptoBalance
import com.greyhairredbear.racooin.core.model.CryptoCurrency
import com.greyhairredbear.racooin.core.model.CryptoCurrencyRate
import com.greyhairredbear.racooin.core.model.FiatBalance
import com.greyhairredbear.racooin.core.model.PersistenceError
import java.time.LocalDateTime

data class CryptoCurrencyRatesWithTimestamp(
    val timeOfFetching: LocalDateTime,
    val rates: List<CryptoCurrencyRate>,
)

interface Persistence {
    suspend fun persistCryptoBalance(balance: CryptoBalance): Either<PersistenceError, Unit>
    suspend fun fetchCryptoBalance(currency: CryptoCurrency): Either<PersistenceError, CryptoBalance>
    suspend fun fetchAllCryptoBalances(): Either<PersistenceError, List<CryptoBalance>>

    suspend fun persistInvest(
        currency: CryptoCurrency,
        balance: FiatBalance
    ): Either<PersistenceError, Unit>

    suspend fun fetchInvest(currency: CryptoCurrency): Either<PersistenceError, FiatBalance>
    suspend fun fetchAllInvests(): Either<PersistenceError, List<FiatBalance>>

    suspend fun persistCurrencyRates(rates: List<CryptoCurrencyRate>): Either<PersistenceError, Unit>
    suspend fun fetchCurrencyRates(): Either<PersistenceError, CryptoCurrencyRatesWithTimestamp>
}
