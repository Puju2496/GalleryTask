package com.example.gallery.datamodel

import com.google.gson.annotations.SerializedName

data class Home(
    @field:SerializedName("page")
    val page: Int = 0,

    @field:SerializedName("pages")
    val pages: Int = 0,

    @field:SerializedName("perpage")
    val perPage: Int = 0,

    @field:SerializedName("total")
    val totalPhotos: Int = 0,

    @field:SerializedName("photo")
    val photoList: List<PhotoDetail>? = null
)
