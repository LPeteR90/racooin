package com.greyhairredbear.racooin.persistence.conversion

import com.greyhairredbear.racooin.core.model.CryptoBalance
import com.greyhairredbear.racooin.persistence.CryptoBalance as PersistenceCryptoBalance

fun CryptoBalance.toPersistenceBalance(): PersistenceCryptoBalance =
    PersistenceCryptoBalance.newBuilder()
        .setCryptoCurrency(cryptoCurrency.toPersistenceModel())
        .setBalance(balance)
        .build()

fun PersistenceCryptoBalance.toCoreModel(): CryptoBalance =
    CryptoBalance(cryptoCurrency.toCoreModel(), balance)
