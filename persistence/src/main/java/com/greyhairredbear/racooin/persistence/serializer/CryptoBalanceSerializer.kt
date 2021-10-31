package com.greyhairredbear.racooin.persistence.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.greyhairredbear.racooin.persistence.CryptoBalances
import java.io.InputStream
import java.io.OutputStream

object CryptoBalanceSerializer: Serializer<CryptoBalances> {
    override val defaultValue: CryptoBalances = CryptoBalances.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): CryptoBalances {
        try {
            return CryptoBalances.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: CryptoBalances, output: OutputStream) = t.writeTo(output)
}
