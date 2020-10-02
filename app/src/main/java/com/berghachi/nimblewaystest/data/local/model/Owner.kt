package com.berghachi.nimblewaystest.data.local.model

import com.google.gson.annotations.SerializedName

data class Owner(@SerializedName("avatar_url")
                 val avatarUrl: String = "",
                 @SerializedName("id")
                 val idOwner: Int = 0,
                 @SerializedName("login")
                 val login: String = "",
                 @SerializedName("url")
                 val url: String = "",
                )