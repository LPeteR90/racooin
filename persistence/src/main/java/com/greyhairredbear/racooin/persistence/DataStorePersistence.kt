package com.greyhairredbear.racooin.persistence

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import arrow.core.Either
import arrow.core.Either.Companion.catch
import com.google.protobuf.Timestamp
import com.greyhairredbear.racooin.core.interfaces.CryptoCurrencyRatesWithTimestamp
import com.greyhairredbear.racooin.core.interfaces.Persistence
import com.greyhairredbear.racooin.core.model.CryptoBalance
import com.greyhairredbear.racooin.core.model.CryptoCurrency
import com.greyhairredbear.racooin.core.model.CryptoCurrencyRate
import com.greyhairredbear.racooin.core.model.FiatBalance
import com.greyhairredbear.racooin.core.model.FiatCurrency
import com.greyhairredbear.racooin.core.model.PersistenceError
import com.greyhairredbear.racooin.persistence.conversion.toCoreModel
import com.greyhairredbear.racooin.persistence.conversion.toPersistenceBalance
import com.greyhairredbear.racooin.persistence.conversion.toPersistenceModel
import com.greyhairredbear.racooin.persistence.serializer.CryptoBalanceSerializer
import com.greyhairredbear.racooin.persistence.serializer.CryptoCurrencyRateSerializer
import com.greyhairredbear.racooin.persistence.serializer.InvestSerializer
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDateTime
import java.time.ZoneOffset
import com.greyhairredbear.racooin.persistence.CryptoBalance as PersistenceCryptoBalance
import com.greyhairredbear.racooin.persistence.CryptoCurrency as PersistenceCryptoCurrency
import com.greyhairredbear.racooin.persistence.FiatBalance as PersistenceFiatBalance

class DataStorePersistence(applicationContext: Context) : Persistence {
    private val cryptoBalancesStore = applicationContext.cryptoBalancesStore
    private val investsStore = applicationContext.investsStore
    private val ratesStore = applicationContext.ratesStore

    override suspend fun persistCryptoBalance(balance: CryptoBalance): Either<PersistenceError, Unit> =
        catchAsPersistenceError {
            cryptoBalancesStore.updateData { it.with(balance.toPersistenceBalance()) }
        }

    override suspend fun fetchCryptoBalance(currency: CryptoCurrency): Either<PersistenceError, CryptoBalance> =
        catchAsPersistenceError {
            cryptoBalancesStore.data.firstOrNull()
                ?.balancesList
                ?.firstOrNull { it.cryptoCurrency == currency.toPersistenceModel() }
                ?.let { CryptoBalance(it.cryptoCurrency.toCoreModel(), it.balance) }
                ?: CryptoBalance(currency, 0.0)
        }

    override suspend fun fetchAllCryptoBalances(): Either<PersistenceError, List<CryptoBalance>> =
        catchAsPersistenceError {
            cryptoBalancesStore.data.firstOrNull()
                ?.balancesList
                ?.map { it.toCoreModel() }
                ?: listOf()
        }

    override suspend fun persistInvest(
        currency: CryptoCurrency,
        balance: FiatBalance
    ): Either<PersistenceError, Unit> =
        catchAsPersistenceError {
            investsStore.updateData {
                it.with(
                    currency.toPersistenceModel(),
                    balance.toPersistenceModel()
                )
            }
        }

    override suspend fun fetchInvest(currency: CryptoCurrency): Either<PersistenceError, FiatBalance> =
        catchAsPersistenceError {
            investsStore.data.firstOrNull()
                ?.investsList
                ?.firstOrNull { it.cryptoCurrency == currency.toPersistenceModel() }
                ?.fiatBalance?.toCoreModel()
                ?: FiatBalance(
                    FiatCurrency.EURO,
                    0.0
                ) // TODO: support multiple fiat currencies here
        }

    override suspend fun fetchAllInvests(): Either<PersistenceError, List<FiatBalance>> =
        catchAsPersistenceError {
            investsStore.data.firstOrNull()
                ?.investsList
                ?.map { it.fiatBalance.toCoreModel() }
                ?: listOf()
        }

    override suspend fun persistCurrencyRates(
        rates: List<CryptoCurrencyRate>
    ): Either<PersistenceError, Unit> =
        catchAsPersistenceError {
            ratesStore.updateData {
                CryptoCurrencyRates.newBuilder()
                    .setTimeOfFetching(LocalDateTime.now().toTimestamp())
                    .addAllRates(rates.map { it.toPersistenceModel() })
                    .build()
            }
        }

    override suspend fun fetchCurrencyRates(): Either<PersistenceError, CryptoCurrencyRatesWithTimestamp> =
        catchAsPersistenceError {
            ratesStore.data.firstOrNull()
                ?.let { rates ->
                    CryptoCurrencyRatesWithTimestamp(
                        rates.timeOfFetching.toLocalDateTime(),
                        rates.ratesList.map { it.toCoreModel() },
                    )
                }
                ?: CryptoCurrencyRatesWithTimestamp(
                    LocalDateTime.now().minusYears(1),
                    listOf()
                )
        }
}

private fun CryptoBalances.with(newBalance: PersistenceCryptoBalance): CryptoBalances =
    CryptoBalances.newBuilder().addAllBalances(
        balancesList.toMutableList().apply {
            removeAll { it.cryptoCurrency == newBalance.cryptoCurrency }
            add(newBalance)
        }
    ).build()

private fun Invests.with(
    cryptoCurrency: PersistenceCryptoCurrency,
    newInvest: PersistenceFiatBalance
): Invests =
    Invests.newBuilder().addAllInvests(
        investsList.apply {
            removeAll { it.cryptoCurrency == cryptoCurrency } // TODO support multiple fiat currencies
            add(
                Invest.newBuilder()
                    .setCryptoCurrency(cryptoCurrency)
                    .setFiatBalance(newInvest)
                    .build()
            )
        }
    ).build()

private fun LocalDateTime.toTimestamp(): Timestamp =
    toInstant(ZoneOffset.UTC)
        .let {
            Timestamp.newBuilder()
                .setSeconds(it.epochSecond)
                .setNanos(it.nano)
                .build()
        }

private fun Timestamp.toLocalDateTime(): LocalDateTime =
    LocalDateTime.ofEpochSecond(seconds, nanos, ZoneOffset.UTC)

private suspend fun <T> catchAsPersistenceError(block: suspend () -> T): Either<PersistenceError, T> =
    catch({ PersistenceError }, { block() })

private const val DATA_STORE_FILENAME_BALANCES = "balances.pb"
private val Context.cryptoBalancesStore: DataStore<CryptoBalances> by dataStore(
    fileName = DATA_STORE_FILENAME_BALANCES,
    serializer = CryptoBalanceSerializer
)

private const val DATA_STORE_FILENAME_INVESTS = "invests.pb"
private val Context.investsStore: DataStore<Invests> by dataStore(
    fileName = DATA_STORE_FILENAME_INVESTS,
    serializer = InvestSerializer
)

private const val DATA_STORE_FILENAME_RATES = "rates.pb"
private val Context.ratesStore: DataStore<CryptoCurrencyRates> by dataStore(
    fileName = DATA_STORE_FILENAME_RATES,
    serializer = CryptoCurrencyRateSerializer
)
