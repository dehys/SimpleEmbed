package com.dehys.simpleembed.types

import com.google.gson.annotations.SerializedName
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import java.time.Instant

@Suppress("unused")
data class RawEmbed(
    var title: String? = null,
    var url: String? = null,
    var description: String? = null,
    var timestamp: String? = null,
    var color: Int? = null,
    var author: SEAuthor? = null,
    @SerializedName(value = "fields", alternate = ["fieldList", "fieldArray"])
    var fieldList: List<SEField>? = null,
    @SerializedName(value = "image_url", alternate = ["image", "imageUrl"])
    var imageUrl: String? = null,
    @SerializedName(value = "thumbnail_url", alternate = ["thumbnail", "thumbnailUrl"])
    var thumbnailUrl: String? = null,
    var footer: SEFooter? = null
) {

    fun toEmbedBuilder() : EmbedBuilder {
        return EmbedBuilder().also { b ->
            b.setTitle(title, url)
            b.setDescription(description)
            b.setTimestamp(timestamp?.let { Instant.parse(it) })
            b.setColor(color ?: 536870911)
            b.setImage(imageUrl)
            b.setThumbnail(thumbnailUrl)
            b.setAuthor(author?.name, author?.url, author?.iconUrl)
            b.setFooter(footer?.text, footer?.iconUrl)
            fieldList?.forEach {
                b.addField(it.name, it.value, it.inline)
            }
        }
    }

    fun asMessageEmbed() : MessageEmbed {
        return toEmbedBuilder().build()
    }
}