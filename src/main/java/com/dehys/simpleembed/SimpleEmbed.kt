package com.dehys.simpleembed

import com.dehys.simpleembed.types.RawEmbed
import com.google.gson.Gson
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import java.awt.Color
import java.io.File

@Suppress("unused", "MemberVisibilityCanBePrivate")
class SimpleEmbed(jda: JDA? = null) : ListenerAdapter() {

    private val files = mutableListOf<File>()
    private var workingDir = System.getProperty("user.dir")+"/embeds"

    private val commandData: List<CommandData> = listOf(
        CommandData("se_embeds", "displays all embeds"),
        CommandData("se_dir", "displays the working directory")
    )

    init {
        workingDir = workingDir.let {
                File(it).apply {
                    mkdir()
                    this.listFiles()?.let { listFiles ->
                        files.addAll(listFiles)
                    }
                }
        }.absolutePath

        jda?.let {
            jda.addEventListener(this)
            jda.awaitReady()
            jda.guilds.onEach { it.updateCommands().addCommands(commandData).complete() }
        }
    }

    /*
    *TODO:
    *   Get json files from resource folder instead of working directory.
    *   In production, the files wont exist in the working directory but
    *   will be in the resources folder as they are compiled.
    */
    override fun onSlashCommand(event: SlashCommandEvent) {
        when (event.name) {
            "se_embeds" -> EmbedBuilder().data(getAllEmbeds()).let { event.replyEmbeds(it.build()).complete() }
            "se_dir" -> event.reply("Working directory:  `$workingDir`").complete()
        }
    }

    private fun getFile(name: String, dir: String?): File {

        if (dir != null) {
            val files = File(dir).listFiles()
            files?.let {
                for (file in it) {
                    if (file.name == name) {
                        return file
                    }
                }
            }
            return File("")
        }

        for(file in files){
            if(file.name == name){
                return file
            }
        }
        return File("")
    }

    private fun addFile(file: File) {
        files.forEach {
            if (it.name == file.name) {
                files.remove(it)
            }
        }
        files.add(file)
    }

    private fun deleteFile(name: String): Boolean {
        for(file in files){
            if(file.name == name){
                files.remove(file)
                return true
            }
        }
        return false
    }

    private fun convertJsonToRawEmbed(json: String): RawEmbed? {
        return Gson().fromJson(json, RawEmbed::class.java) ?: null
    }

    private fun convertJsonToRawEmbed(file: File): RawEmbed? {
        return Gson().fromJson(file.readText(), RawEmbed::class.java) ?: null
    }

    //Get embed from Message.Attachment
    fun getEmbedBuilder(attachment: Message.Attachment): EmbedBuilder {
        if(
            attachment.isImage ||
            attachment.isVideo ||
            attachment.fileExtension == null ||
            !attachment.fileExtension.equals("json", true)
        ) return EmbedBuilder().error("Invalid file type")

        val dwFile = attachment.downloadToFile(workingDir+ File.separator +attachment.fileName).get()
        addFile(dwFile)
        return convertJsonToRawEmbed(dwFile)?.toEmbedBuilder() ?: EmbedBuilder().error("Invalid file type")
    }

    fun getEmbedBuilder(name: String, dir: String?): EmbedBuilder {
        val file = getFile(name, dir)
        if(file.exists() && file.isFile && file.extension == "json"){
            return convertJsonToRawEmbed(file)?.toEmbedBuilder() ?: EmbedBuilder().error("Invalid file type")
        }
        return EmbedBuilder().error("File not found")
    }

    fun getEmbedBuilder(file: File): EmbedBuilder {
        if(file.exists() && file.isFile && file.extension == "json"){
            return convertJsonToRawEmbed(file)?.toEmbedBuilder() ?: EmbedBuilder().error("Invalid file type")
        }
        return EmbedBuilder().error("File not found")
    }

    fun getEmbedBuilders(attachments: List<Message.Attachment>): List<EmbedBuilder> {
        val embeds = mutableListOf<EmbedBuilder>()
        attachments.forEach {
            embeds.add(getEmbedBuilder(it))
        }
        return embeds
    }

    fun getEmbedBuilders(message: Message): List<EmbedBuilder> {
        return getEmbedBuilders(message.attachments)
    }

    fun getMessageEmbed(attachment: Message.Attachment): MessageEmbed {
        return getEmbedBuilder(attachment).build()
    }

    fun getMessageEmbed(name: String, dir: String?): MessageEmbed {
        return getEmbedBuilder(name, dir).build()
    }

    fun getMessageEmbed(file: File): MessageEmbed {
        return getEmbedBuilder(file).build()
    }

    fun getMessageEmbeds(attachments: List<Message.Attachment>): List<MessageEmbed> {
        val embeds = mutableListOf<MessageEmbed>()
        attachments.forEach {
            embeds.add(getMessageEmbed(it))
        }
        return embeds
    }

    fun getMessageEmbeds(message: Message): List<MessageEmbed> {
        return getMessageEmbeds(message.attachments)
    }

    fun getRawEmbed(name: String, dir: String?): RawEmbed? {
        return convertJsonToRawEmbed(getFile(name, dir))
    }

    fun deleteEmbed(name: String): Boolean {
        return deleteFile(name)
    }

    fun getAllEmbeds(): Array<String> {
        val embeds = mutableListOf<String>()
        for(file in files){
            embeds.add(file.name)
        }
        return embeds.toTypedArray()
    }

    //WARNING: This makes the type classes of SimpleEmbed public
    /*fun convertToRaw(embedBuilder: EmbedBuilder) : RawEmbed {
        val me = embedBuilder.build()

        val author = Author().also {
            it.name = me.author?.name
            it.url = me.author?.url
            it.iconUrl = me.author?.iconUrl
        }

        val fields = mutableListOf<com.dehys.simpleembed.types.Field>().also {
            me.fields.forEach { field ->
                it.add(com.dehys.simpleembed.types.Field(field.name, field.value, field.isInline))
            }
        }

        val footer = Footer().also {
            it.text = me.footer?.text
            it.iconUrl = me.footer?.iconUrl
        }

        var timestamp: String? = null
        if (me.timestamp != null && me.timestamp.toString() != "null") {
            timestamp = me.timestamp.toString()
        }

        return RawEmbed(
            title = me.title,
            url = me.url,
            description = me.description,
            timestamp = timestamp,
            color = me.colorRaw,
            author = author,
            fieldList = fields,
            imageUrl = me.image?.url,
            thumbnailUrl = me.thumbnail?.url,
            footer = footer
        )
    }*/

    fun EmbedBuilder.error(error: String): EmbedBuilder {
        this.setColor(Color.RED)
        this.setTitle("SimpleEmbed")
        this.setDescription("**ERROR**: `$error`")
        return this
    }

    fun EmbedBuilder.data(strings: Array<String>) : EmbedBuilder {
        val embed = this
            .setColor(Color.CYAN)
            .setTitle("Listing all data", "https://github.com/dehys/SimpleEmbed")
        val desc = StringBuilder()
        for (i in strings.indices) {
            desc.append("``[${i + 1}] ${strings[i]}``\n")
        }
        return embed.setDescription(desc.toString())
    }
}