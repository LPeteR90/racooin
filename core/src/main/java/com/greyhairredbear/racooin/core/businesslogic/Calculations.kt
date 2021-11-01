package com.greyhairredbear.racooin.core.businesslogic

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import arrow.core.traverseEither
import com.greyhairredbear.racooin.core.model.ComputationFailedError
import com.greyhairredbear.racooin.core.model.CryptoBalance
import com.greyhairredbear.racooin.core.model.CryptoCurrencyRate
import com.greyhairredbear.racooin.core.model.CryptoFiatBalance
import com.greyhairredbear.racooin.core.model.FiatBalance

// TODO
//  test
//  error modelling
fun CryptoBalance.withFiatBalance(
    rate: CryptoCurrencyRate
): Either<ComputationFailedError, CryptoFiatBalance> {
    return when {
        cryptoCurrency != rate.cryptoCurrency -> ComputationFailedError.left()
        else -> CryptoFiatBalance(
            cryptoBalance = this.copy(),
            fiatBalance = toFiatBalance(rate.fiatBalance)
        ).right()
    }
}

// TODO: test?
fun CryptoBalance.toFiatBalance(rate: FiatBalance): FiatBalance =
    rate.copy(balance = balance * rate.balance)

// TODO: support multiple rates
fun List<CryptoBalance>.withFiatBalances(
    rates: List<CryptoCurrencyRate>
): Either<ComputationFailedError, List<CryptoFiatBalance>> =
    traverseEither { balance ->
        balance.withFiatBalance(
            rates.firstOrNull { it.cryptoCurrency == balance.cryptoCurrency }
                ?: return ComputationFailedError.left()
        )
    }

// TODO: test
fun CryptoFiatBalance.toCurrencyRate(): CryptoCurrencyRate =
    CryptoCurrencyRate(
        cryptoCurrency = cryptoBalance.cryptoCurrency,
        fiatBalance = fiatBalance.copy(balance = fiatBalance.balance / cryptoBalance.balance)
    )
