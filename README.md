# SimpleEmbed

This is a utility library to convert json files to EmbedBuilder objects from JDA.
The entire application is contained within the `SimpleEmbed` class.
`embeds/` is the directory where the json files are stored, you can delete and override them whenever you want.
It uses `Lombok`, `Gson` and `JDA` as dependencies.

## Install

    $ git clone https://github.com/dehys/SimpleEmbed.git && cd SimpleEmbed
    $ mvn clean install

## Methods
`SimpleEmbed.init()` - Initializes the library. Creates the embeds directory if it doesn't exist and loads all the json files.</br>
`SimpleEmbed.get(String)` - Tries to load an EmbedBuilder object from the specified String, returns null if json file was not found.</br>
`SimpleEmbed.getFiles()` - Returns a list of all the json files loaded by SimpleEmbed.</br>
`SimpleEmbed.create(List<Message.Attachment>)` - Creates an EmbedBuilder object from a list of Message.Attachment objects.</br>
`SimpleEmbed.delete(String)` - Deletes the specified json file.</br>

## Json
```json
 {
   "title": "This is an example embed",
   "description": "This is a test embed to showcase what embeds look like and how they work.",
   "url": "https://github.com/dehys/SimpleEmbed",
   "color": 15158332,
   "author": {
     "name": "Dehys",
     "url": "https://github.com/dehys/SimpleEmbed",
     "icon_url": "https://avatars.githubusercontent.com/u/23004445?v=4.png"
   },
   "fields": [
     {
       "name": "This is a field",
       "value": "This is the value of the field."
     },
     {
       "name": "This is another field",
       "value": "This is the value of the field.",
       "inline": true
     }
   ],
   "image": "https://picsum.photos/200/200",
   "thumbnail": "https://picsum.photos/200/200"
 }
```

The above json example will output the following embed:</br>
<img src="https://i.imgur.com/CR3SnRZ.png" width="800px">

## Example usage
### *Main.class*

```java

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Main {
    public static void main(String[] args) throws InterruptedException, LoginException {

        // Create a new JDA instance with the token of your bot
        JDA jda = JDABuilder.createDefault(args[0]).build();
        // Register the listener with the JDA instance
        jda.addEventListener(new ExampleListener());

        // Initialize SimpleEmbed
        SimpleEmbedOld.init();
    }
}
```

### *ExampleListener.class*

```java

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ExampleListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().startsWith("!")) {

            // Get the embed from the json file
            EmbedBuilder embed = SimpleEmbedOld.get("example.json");
            if (embed != null) {
                // Send the embed to the channel
                event.getChannel().sendMessage(embed.build()).queue();
            }

        }
    }
}
```

## License 
This project is licensed under the MIT license.</br>
More information can be found in the LICENSE file.
</br>
</br>
</br>
</br>
Made with ❤ by [dehys](https://github.com/dehys) </br>
Join the discord server for support: https://discord.gg/SxwUsgk </br>

