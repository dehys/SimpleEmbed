package com.dehys.simpleembed.types

import com.google.gson.annotations.SerializedName

internal class Author {

    var name: String? = null
    var url: String? = null
    @SerializedName(value = "icon_url", alternate = ["icon", "iconUrl"])
    var iconUrl: String? = null

}




