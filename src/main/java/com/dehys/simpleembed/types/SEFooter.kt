package com.dehys.simpleembed.types

import com.google.gson.annotations.SerializedName

data class SEFooter(
    var text: String? = null,
    @SerializedName("icon_url", alternate = ["icon", "iconUrl"])
    var iconUrl: String? = null
)