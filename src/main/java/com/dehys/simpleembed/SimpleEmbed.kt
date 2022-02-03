package com.dehys.simpleembed

import com.dehys.simpleembed.types.RawEmbed
import com.google.gson.Gson
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import java.io.File

class SimpleEmbed(jda: JDA? = null) : ListenerAdapter() {

    private val files = mutableListOf<File>()
    private var workingDir = System.getProperty("user.dir")+"/embeds"

    private val commandData: List<CommandData> = listOf(
        CommandData("se_embeds", "displays all embeds"),
        CommandData("se_files", "displays all files"),
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

        files.forEach(::println)

        jda?.let { jd_a ->
            jd_a.addEventListener(this)
            jd_a.awaitReady()
            jd_a.guilds.onEach { it.updateCommands().addCommands(commandData).complete() }
            println("initialized SimpleEmbed with jda")
        }
    }

    override fun onSlashCommand(event: SlashCommandEvent) {
        when (event.name) {
            "se_embeds" -> getEmbed("embeds.json")?.let { event.replyEmbeds(it.build()).complete() }
            "se_files" -> getEmbed("files.json")?.let { event.replyEmbeds(it.build()).complete() }
            "se_dir" -> event.reply("Working directory:  `$workingDir`").complete()
        }
    }

    private fun getFiles(): MutableList<File> {
        return files
    }

    //get file by name from files
    private fun getFile(name: String): File {
        for(file in files){
            if(file.name == name){
                return file
            }
        }
        return File("")
    }

    //add file to files
    private fun addFile(file: File) {
        files.add(file)
    }

    //delete file by name from files
    private fun deleteFile(name: String): Boolean {
        for(file in files){
            if(file.name == name){
                files.remove(file)
                return true
            }
        }
        return false
    }

    private fun generateEmbed(file: File): EmbedBuilder {
        return Gson().fromJson(file.readText(), RawEmbed::class.java).build()
    }

    //get embed from Message Attachments
    fun getEmbed(message: Message): EmbedBuilder? {
        if(message.attachments.isEmpty() || message.author.isBot || message.author.isSystem) return null

        for (atc in message.attachments) {
            if(
                atc.isImage ||
                atc.isVideo ||
                atc.fileExtension == null ||
                !atc.fileExtension.equals("json", true)
            ) return null


            return if(getFile(atc.fileName).exists()) {
                generateEmbed(getFile(atc.fileName))
            } else {
                val file = atc.downloadToFile(workingDir+"/embeds/"+atc.fileName).get()
                files.add(file)
                return generateEmbed(file)
            }
        }

        return null
    }

    //get embed from files by file name
    fun getEmbed(name: String): EmbedBuilder? {
        val file = getFile(name)
        if(file.exists() && file.isFile && file.extension == "json"){
            return generateEmbed(file)
        }
        return null
    }

    //delete embed from files by file name
    fun deleteEmbed(name: String): Boolean {
        return deleteFile(name)
    }

    //get all embeds from files as String array of file names
    fun getAllEmbeds(): Array<String> {
        val embeds = mutableListOf<String>()
        for(file in files){
            embeds.add(file.name)
        }
        return embeds.toTypedArray()
    }

}