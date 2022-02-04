package com.dehys.simpleembed.types

import com.google.gson.annotations.SerializedName

internal data class Footer(
    var text: String? = null,
    @SerializedName("icon_url", alternate = ["icon", "iconUrl"])
    var iconUrl: String? = null
)