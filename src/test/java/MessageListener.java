import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (
                        !event.isFromGuild() ||
                        event.getChannel().getIdLong() != 930385470171545610L ||
                        event.getAuthor().isBot() ||
                        event.getMessage().getAttachments().isEmpty()
        ) return;

        EmbedBuilder builder = Bot.simpleEmbed.getEmbed(event.getMessage());
        assert builder != null;
        event.getChannel().sendMessageEmbeds(builder.build()).queue();

    }
}
