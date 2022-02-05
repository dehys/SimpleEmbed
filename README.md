<br>
<br>
<div align="center" id="top"> 
  <img src="https://i.imgur.com/GD3Huxq.png" width="800px" alt="<h1>SimpleEmbed</h1>" />
</div>
<p align="center">
  <a href="https://discord.gg/DdnayChh4g"><img alt="Discord" src="https://img.shields.io/discord/435431724831211522?color=%237289DA&label=%20%E2%80%8E%20%E2%80%8E%20%E2%80%8EDiscord%20%E2%80%8E&logo=Discord&logoColor=%237289DA&style=flat-square"></a>
  <a href="https://github.com/devflask/StatisticsLib/releases"><img alt="Releases" src="https://img.shields.io/github/v/release/dehys/SimpleEmbed?color=%2354f95f&label=Latest%20Release&logo=GitHub&logoColor=%2354f95f&style=flat-square"></a>
  <a href="https://en.wikipedia.org/wiki/MIT_License"><img alt="Discord" src="https://img.shields.io/github/license/devflask/StatisticsLib?color=%23f9a154&label=License&style=flat-square"></a>
</p>
<p align="center">
  <a href="https://github.com/dehys/SimpleEmbed/#implementation">Implementation</a> &#xa0; | &#xa0;
  <a href="https://github.com/dehys/SimpleEmbed/#dependencies">Dependencies</a> &#xa0; | &#xa0;
  <a href="https://github.com/dehys/SimpleEmbed/#getting-started">Getting Started</a> &#xa0; | &#xa0;
  <a href="https://github.com/dehys/SimpleEmbed/#json-example">Json Example</a> &#xa0; | &#xa0;
  <a href="https://github.com/dehys/SimpleEmbed/#license">License</a> &#xa0;
</p>

## Implementation ##
Let's install the library so we can use it. First we clone the repository and then build it with maven

```
$ git clone https://github.com/dehys/SimpleEmbed.git && cd SimpleEmbed
$ mvn clean install
```
<br>
Now lets configure our pom.xml (Maven), so we can actually use the library

```xml
<dependencies>
  <dependency>
    <groupId>com.dehys</groupId>
    <artifactId>SimpleEmbed</artifactId>
    <version>VERSION</version>
    <scope>compile</scope>
  </dependency>
</dependencies>
```
<br>

## Dependencies ##
- [JDA version 5 or newer](https://github.com/DV8FromTheWorld/JDA/releases)
<br>

## Getting Started ##
Quick links: [Kotlin Example](https://github.com/dehys/SimpleEmbed/blob/36ea2729ba64d2b9d0599deccfb535e6ecfcc872/src/main/java/com/dehys/simpleembed/examples/KotlinExample.kt#L13) | [Java Example](https://github.com/dehys/SimpleEmbed/blob/36ea2729ba64d2b9d0599deccfb535e6ecfcc872/src/main/java/com/dehys/simpleembed/examples/JavaExample.java#L15)

In this example we will send a message to a discord channel with an attachment of a json file.<br>
SimpleEmbed will take the file and try to convert it to a EmbedBuilder object which we can<br>
then use to reply in the same channel. Let's start in the **Bot** class:<br>
```kotlin
var simpleEmbed: SimpleEmbed? = null

init {
    jda.addEventListener(ExampleListener(this)) // Add the listener to the JDA instance
    simpleEmbed = SimpleEmbed(jda) // Create a new SimpleEmbed instance, optionally passing a JDA instance
}
```
We can optionally pass an instance of the JDA object. If we do, SimpleEmbed is going to add some useful<br>
slash commands, for example: `/se_embeds` to list all the embeds in the channel, `/se_files` to list all the files stored,<br>
and `/se_dir` to see the working directory of SimpleEmbed.

Now lets see what the **ExampleListener** class looks like:
```kotlin
override fun onMessageReceived(event: MessageReceivedEvent) {
    if (event.author.isBot) return
    if (event.message.attachments.isEmpty()) return

    // Get all embeds from the message as a list of EmbedBuilder, then map them to a list of MessageEmbed by calling build() on each one.
    val embeds = bot.simpleEmbed?.getMessageEmbeds(event.message.attachments) ?: emptyList()

    // Send the embeds to the channel the message was sent in.
    embeds.forEach { event.channel.sendMessageEmbeds(it).queue() }
}
```
The above example show's you how to use SimpleEmbed in Kotlin, but If you want to use it in java, you can check out all the examples [here](https://github.com/dehys/SimpleEmbed/tree/main/src/main/java/com/dehys/simpleembed/examples)
<br>

## Json Example ##
This is an example of a valid json file.
```json
{
    "title": "SimpleEmbed",
    "url": "https://github.com/dehys/SimpleEmbed",
    "description": "Hello there, this is an embed to test the SimpleEmbed library. If you are currently seeing this data as an 'Embed' that means that the json file containing this data was sucessfully converted to an Embed",
    "color": 16541647,
    "author": {
        "name": "dehys",
        "url": "https://github.com/dehys",
        "icon": "https://avatars.githubusercontent.com/u/23004445?v=4.png"
    },
    "fields": [
        {
            "name": "Github",
            "value": "[Click Here](https://github.com/dehys/SimpleEmbed)",
            "inline": true
        },
        {
            "name": "Discord",
            "value": "[Click Here](https://discord.gg/SxwUsgk)",
            "inline": true
        }
    ],
    "image": "https://i.imgur.com/GD3Huxq.png",
    "thumbnail": "https://avatars.githubusercontent.com/u/23004445?v=4.png",
    "footer": {
        "text": "Kotlin | Made with ❤️ by dehys",
        "icon": "https://i.imgur.com/5kBrb5C.png"
    }
}
```
<br>

## License ##
This project is licensed under the MIT license.</br>
More information can be found in the [LICENSE](https://github.com/dehys/SimpleEmbed/blob/main/LICENSE) file.
