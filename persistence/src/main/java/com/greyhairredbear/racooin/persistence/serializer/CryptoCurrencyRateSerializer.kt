package com.greyhairredbear.racooin.persistence.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.greyhairredbear.racooin.persistence.CryptoCurrencyRates
import java.io.InputStream
import java.io.OutputStream

object CryptoCurrencyRateSerializer : Serializer<CryptoCurrencyRates> {
    override val defaultValue: CryptoCurrencyRates = CryptoCurrencyRates.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): CryptoCurrencyRates {
        try {
            return CryptoCurrencyRates.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: CryptoCurrencyRates, output: OutputStream) = t.writeTo(output)
}
