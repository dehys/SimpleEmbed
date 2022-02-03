package com.dehys.simpleembed.types

import com.google.gson.annotations.SerializedName

internal class Footer {

    private var text: String? = null
    @SerializedName("icon_url", alternate = ["icon", "iconUrl"])
    private var iconUrl: String? = null

}