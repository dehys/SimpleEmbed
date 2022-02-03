import com.dehys.simpleembed.SimpleEmbed;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Bot {

    public static JDA jda;
    public static SimpleEmbed simpleEmbed;

    public static void main(String[] args) throws LoginException {

        jda = JDABuilder.createDefault(System.getenv("SKIPBABE_TOKEN")).build();
        simpleEmbed = new SimpleEmbed(jda);
        jda.addEventListener(new MessageListener());

    }

}
