package com.dehys.simpleembed.types;

import lombok.Data;
import net.dv8tion.jda.api.EmbedBuilder;

@Data
public
class SEEmbed {

    String title = "";
    String description = "";
    String url = "";
    String color = "";
    SEAuthor author = null;
    SEField[] fields = null;
    String image = "";
    String thumbnail = "";

    public EmbedBuilder toEmbedBuilder(){
        EmbedBuilder eb = new EmbedBuilder();
        if (!title.isBlank()){
            if (!url.isBlank()) eb.setTitle(title, url);
            else eb.setTitle(title);
        }
        if (!description.isBlank()) eb.setDescription(description);
        if (!color.isBlank()) eb.setColor(Integer.parseInt(color));
        if (author != null) {
            if (!author.getName().isBlank()){
                if (!author.getUrl().isBlank()){
                    if (!author.getIcon_url().isBlank()) eb.setAuthor(author.getName(), author.getUrl(), author.getIcon_url());
                    else eb.setAuthor(author.getName(), author.getUrl());
                } else eb.setAuthor(author.getName());
            }
        }
        if (fields != null){
            for (SEField f : fields) {
                if (!(f.getName().isBlank() && f.getValue().isBlank())) eb.addField(f.getName(), f.getValue(), f.isInline());
                else eb.addBlankField(f.isInline());
            }
        }
        if (!image.isBlank()) eb.setImage(image);
        if (!thumbnail.isBlank()) eb.setThumbnail(thumbnail);
        return eb;
    }

}
