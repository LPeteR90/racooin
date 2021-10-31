package com.greyhairredbear.racooin.persistence.conversion

import com.greyhairredbear.racooin.core.model.CryptoCurrencyRate
import com.greyhairredbear.racooin.persistence.CryptoCurrencyRate as PersistenceCryptoCurrencyRate

fun CryptoCurrencyRate.toPersistenceModel(): PersistenceCryptoCurrencyRate =
    PersistenceCryptoCurrencyRate.newBuilder()
        .setCryptoCurrency(cryptoCurrency.toPersistenceModel())
        .setFiatBalance(fiatBalance.toPersistenceModel())
        .build()

fun PersistenceCryptoCurrencyRate.toCoreModel(): CryptoCurrencyRate =
    CryptoCurrencyRate(cryptoCurrency.toCoreModel(), fiatBalance.toCoreModel())
