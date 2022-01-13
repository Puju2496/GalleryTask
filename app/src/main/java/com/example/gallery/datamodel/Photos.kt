package com.example.gallery.datamodel

import com.google.gson.annotations.SerializedName

data class Photos(
    @field:SerializedName("photos")
    val photos: Home? = null,

    @field:SerializedName("stat")
    val status: String? = null
)
