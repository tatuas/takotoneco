package com.tatuas.takotoneco.data.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.*

object DateAsStringSerializer : KSerializer<Date> {
    private val format = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss'Z'",
        Locale.US,
    ).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeString(format.format(value))
    }

    override fun deserialize(decoder: Decoder): Date {
        val string = decoder.decodeString()
        return requireNotNull(format.parse(string))
    }
}