package com.greyhairredbear.racooin.persistence.conversion

import com.greyhairredbear.racooin.core.model.CryptoCurrency
import java.io.UnsupportedEncodingException
import com.greyhairredbear.racooin.persistence.CryptoCurrency as PersistenceCryptoCurrency

fun CryptoCurrency.toPersistenceModel(): PersistenceCryptoCurrency =
    when (this) {
        CryptoCurrency.BITCOIN -> PersistenceCryptoCurrency.BITCOIN
        CryptoCurrency.ETHEREUM -> PersistenceCryptoCurrency.ETHEREUM
        CryptoCurrency.DOGECOIN -> PersistenceCryptoCurrency.DOGECOIN
        CryptoCurrency.LITECOIN -> PersistenceCryptoCurrency.LITECOIN
        CryptoCurrency.RIPPLE -> PersistenceCryptoCurrency.RIPPLE
    }

fun PersistenceCryptoCurrency.toCoreModel(): CryptoCurrency =
    when (this) {
        PersistenceCryptoCurrency.BITCOIN -> CryptoCurrency.BITCOIN
        PersistenceCryptoCurrency.ETHEREUM -> CryptoCurrency.ETHEREUM
        PersistenceCryptoCurrency.DOGECOIN -> CryptoCurrency.DOGECOIN
        PersistenceCryptoCurrency.LITECOIN -> CryptoCurrency.LITECOIN
        PersistenceCryptoCurrency.RIPPLE -> CryptoCurrency.RIPPLE
        PersistenceCryptoCurrency.UNRECOGNIZED -> {
            throw UnsupportedEncodingException() // TODO
        }
    }
