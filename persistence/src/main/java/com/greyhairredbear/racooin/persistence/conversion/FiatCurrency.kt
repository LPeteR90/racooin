package com.greyhairredbear.racooin.persistence.conversion

import com.greyhairredbear.racooin.core.model.FiatCurrency
import java.io.UnsupportedEncodingException
import com.greyhairredbear.racooin.persistence.FiatCurrency as PersistenceFiatCurrency

fun FiatCurrency.toPersistenceModel(): PersistenceFiatCurrency =
    when (this) {
        FiatCurrency.EURO -> PersistenceFiatCurrency.EURO
        FiatCurrency.US_DOLLAR -> PersistenceFiatCurrency.US_DOLLAR
    }

fun PersistenceFiatCurrency.toCoreModel(): FiatCurrency =
    when (this) {
        PersistenceFiatCurrency.EURO -> FiatCurrency.EURO
        PersistenceFiatCurrency.US_DOLLAR -> FiatCurrency.US_DOLLAR
        PersistenceFiatCurrency.UNRECOGNIZED -> {
            throw UnsupportedEncodingException() // TODO
        }
    }
