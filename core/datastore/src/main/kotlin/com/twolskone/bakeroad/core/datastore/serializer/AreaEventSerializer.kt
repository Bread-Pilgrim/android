package com.twolskone.bakeroad.core.datastore.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.twolskone.bakeroad.core.datastore.AreaEvent
import java.io.InputStream
import java.io.OutputStream

/**
 * An [androidx.datastore.core.Serializer] for the [AreaEvent] proto.
 */
internal object AreaEventSerializer : Serializer<AreaEvent> {

    override val defaultValue: AreaEvent
        get() = AreaEvent.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AreaEvent =
        try {
            // readFrom is already called on the data store background thread.
            AreaEvent.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }

    override suspend fun writeTo(t: AreaEvent, output: OutputStream) {
        // writeTo is already called on the data store background thread.
        t.writeTo(output)
    }
}