package com.dehys.simpleembed;

import com.dehys.simpleembed.types.SEEmbed;
import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class SimpleEmbed {

    private static boolean initialized;
    private static String workingDir;
    private static final List<File> files = new ArrayList<>();

    private static EmbedBuilder create(File file) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(new FileReader(file), SEEmbed.class).toEmbedBuilder();
    }

    public static EmbedBuilder create(List<Message.Attachment> attachments) {
        if (!initialized) {
            System.out.println("[SimpleEmbed] SimpleEmbed is not initialized! Please initialize it with SimpleEmbed#init()");
            return null;
        }

        for (Message.Attachment attachment : attachments){
            if (attachment == null) return null;
            if (attachment.getFileExtension() == null) return null;
            if (attachment.getFileExtension().equalsIgnoreCase("json")){
                try {
                    File file = attachment.downloadToFile(workingDir + File.separator + attachment.getFileName()).get();
                    files.add(file);
                    return create(file);
                } catch (InterruptedException | ExecutionException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static EmbedBuilder get(String fileName) throws IOException {
        File file = null;
        for (File f : files) {
            if (f.getName().equalsIgnoreCase(fileName)){
                file = f;
                break;
            }
        }

        if (file == null) return null; //failed to get file

        return create(file);
    }

    public static List<File> getFiles(){
        return files;
    }

    public static void delete(String fileName){
        File file = null;
        for (File f : files) {
            if (f.getName().equalsIgnoreCase(fileName)){
                file = f;
                break;
            }
        }

        if (file == null) return; //failed to get file

        if (file.exists()) file.delete();
        files.remove(file);
    }

    public static void init(){
        workingDir = System.getProperty("user.dir");
        File embedsDir = new File(workingDir+"/embeds");
        if (embedsDir.exists()){
            System.out.println("[SimpleEmbeds] Initialized successfully");
            initialized = true;
        } else {
            if (embedsDir.mkdirs()){
                System.out.println("[SimpleEmbeds] Created embeds directory and initialized successfully");
                initialized = true;
            } else {
                System.out.println("[SimpleEmbeds] Failed to create embeds directory! Please check permissions");
                initialized = false;
                return;
            }
        }

        workingDir = embedsDir.getAbsolutePath();

        for (File file : Objects.requireNonNull(embedsDir.listFiles())){
            if (file.getName().endsWith(".json")){
                files.add(file);
            }
        }
    }

}
