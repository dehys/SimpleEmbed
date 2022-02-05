package com.dehys.simpleembed.examples;

import com.dehys.simpleembed.SimpleEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.util.List;

public class JavaExample extends ListenerAdapter {
    public static SimpleEmbed simpleEmbed;

    public static void main(String[] args) throws LoginException {
        JDA jda = JDABuilder.createDefault(System.getenv("BOT_TOKEN")).build();
        jda.addEventListener(new JavaExample()); // Add the listener to the JDA instance
        simpleEmbed = new SimpleEmbed(jda); // Create a new SimpleEmbed instance, optionally passing a JDA instance
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.getMessage().getAttachments().isEmpty()) return; // Ignore bots and messages without attachments

        // Get all embeds from the message as a list of EmbedBuilder, then map them to a list of MessageEmbed by calling build() on each one.
        List<MessageEmbed> embeds = simpleEmbed.getMessageEmbeds(event.getMessage().getAttachments());

        // Send the embeds to the channel the message was sent in.
        embeds.forEach(embed -> event.getChannel().sendMessageEmbeds(embed).queue());
    }
}
