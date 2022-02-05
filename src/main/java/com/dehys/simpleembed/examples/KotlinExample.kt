package com.dehys.simpleembed.examples

import com.dehys.simpleembed.SimpleEmbed
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

fun main() {
    Bot(JDABuilder.createDefault(System.getenv("BOT_TOKEN")).build())
}

class Bot(jda: JDA) {
    var simpleEmbed: SimpleEmbed? = null

    init {
        jda.addEventListener(ExampleListener(this)) // Add the listener to the JDA instance
        simpleEmbed = SimpleEmbed(jda) // Create a new SimpleEmbed instance, optionally passing a JDA instance
    }
}

class ExampleListener(private val bot: Bot) : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot || event.message.attachments.isEmpty()) return // Ignore bots and messages without attachments

        // Get all embeds from the message as a list of EmbedBuilder, then map them to a list of MessageEmbed by calling build() on each one.
        val embeds = bot.simpleEmbed?.getMessageEmbeds(event.message.attachments) ?: emptyList()

        // Send the embeds to the channel the message was sent in.
        embeds.forEach { event.channel.sendMessageEmbeds(it).queue() }
    }
}