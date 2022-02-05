package com.dehys.simpleembed.types

import com.google.gson.annotations.SerializedName

data class SEAuthor(
    var name: String? = null,
    var url: String? = null,
    @SerializedName(value = "icon_url", alternate = ["icon", "iconUrl"])
    var iconUrl: String? = null
)




