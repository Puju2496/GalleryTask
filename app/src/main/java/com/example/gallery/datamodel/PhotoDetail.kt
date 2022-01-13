package com.example.gallery.datamodel

import com.google.gson.annotations.SerializedName

data class PhotoDetail(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("owner")
    val owner: String? = null,

    @field:SerializedName("secret")
    val secret: String? = null,

    @field:SerializedName("server")
    val server: String? = null,

    @field:SerializedName("farm")
    val farm: Int = 0,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("ispublic")
    val isPublic: Int = 0,

    @field:SerializedName("isfriend")
    val isFriend: Int = 0,

    @field:SerializedName("isfamily")
    val isFamily: Int = 0,

    @field:SerializedName("url_s")
    val url: String? = null,

    @field:SerializedName("height_s")
    val height: Int = 0,

    @field:SerializedName("width_s")
    val width: Int = 0
)
