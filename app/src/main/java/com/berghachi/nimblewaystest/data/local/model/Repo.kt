package com.berghachi.nimblewaystest.data.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Repo(
    @SerializedName("owner")
    @Embedded
    val owner: Owner,
    @SerializedName("full_name")
    val fullName: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("language")
    val language: String = "",
    @SerializedName("id")
    @PrimaryKey
    val id: Int = 0,
    @SerializedName("node_id")
    val nodeId: String = "",
    @SerializedName("html_url")
    val html_url: String = "",
    var isFavorite: Boolean
)