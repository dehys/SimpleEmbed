package com.dehys.simpleembed.types;

import lombok.Data;

@Data
public class SEField {
    String name;
    String value;
    boolean inline = false;
}
