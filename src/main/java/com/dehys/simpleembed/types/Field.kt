package com.dehys.simpleembed.types

internal data class Field(
    var name: String? = null,
    var value: String? = null,
    var inline: Boolean = false
)