package com.greyhairredbear.racooin.persistence.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.greyhairredbear.racooin.persistence.Invests
import java.io.InputStream
import java.io.OutputStream

object InvestSerializer: Serializer<Invests> {
    override val defaultValue: Invests = Invests.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Invests {
        try {
            return Invests.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: Invests, output: OutputStream) = t.writeTo(output)
}
