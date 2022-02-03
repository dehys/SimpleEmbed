package com.dehys.simpleembed.types

import com.google.gson.annotations.SerializedName
import net.dv8tion.jda.api.EmbedBuilder
import java.time.Instant

internal class RawEmbed(embed: EmbedBuilder?) {

    private var embed: EmbedBuilder = EmbedBuilder()

    private var title: String? = null
    private var url: String? = null
    private var description: String? = null
    private var timestamp: String? = null
    private var color: Int? = null
    private var author: Author? = null
    private var fields: List<Field>? = null
    @SerializedName(value = "image_url", alternate = ["image", "imageUrl"])
    private var imageUrl: String? = null
    @SerializedName(value = "thumbnail_url", alternate = ["thumbnail", "thumbnailUrl"])
    private var thumbnailUrl: String? = null
    private var footer: Footer? = null

    init {
        if (embed != null) {
            this.embed = embed
            TODO("Not yet implemented, convert EmbedBuilder to RawEmbed")
        }
    }

    fun build() : EmbedBuilder {
        //check if variables are null and if not set them with embed
        if (title != null && url != null) embed.setTitle(title, url) else if (title != null) embed.setTitle(title) //Title and title url
        if (description != null) embed.setDescription(description) //Description
        if (timestamp != null) embed.setTimestamp(Instant.parse(timestamp)) //Timestamp
        if (color != null) embed.setColor(color!!) //Color
        if (imageUrl != null) embed.setImage(imageUrl) //Image
        if (thumbnailUrl != null) embed.setThumbnail(thumbnailUrl) //Thumbnail

        if (author != null) {
            if (author!!.name != null && author!!.url != null && author!!.iconUrl != null) embed.setAuthor(author!!.name, author!!.url, author!!.iconUrl)
            else if (author!!.name != null && author!!.url != null) embed.setAuthor(author!!.name, author!!.url)
            else if (author!!.name != null) embed.setAuthor(author!!.name)
        }

        return embed
    }

    private fun EmbedBuilder.getFrom(rawEmbed: RawEmbed) : EmbedBuilder {

        return this
    }

}