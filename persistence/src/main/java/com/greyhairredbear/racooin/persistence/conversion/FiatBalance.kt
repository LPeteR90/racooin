package com.greyhairredbear.racooin.persistence.conversion

import com.greyhairredbear.racooin.core.model.FiatBalance
import com.greyhairredbear.racooin.persistence.FiatBalance as PersistenceFiatBalance

fun FiatBalance.toPersistenceModel(): PersistenceFiatBalance =
    PersistenceFiatBalance.newBuilder()
        .setCurrency(fiatCurrency.toPersistenceModel())
        .setBalance(balance)
        .build()

fun PersistenceFiatBalance.toCoreModel(): FiatBalance =
    FiatBalance(currency.toCoreModel(), balance)
