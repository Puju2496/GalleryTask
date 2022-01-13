package com.example.gallery.model

import android.os.Parcel
import android.os.Parcelable

data class Photo(
    var id: String = "",
    private var layoutId: Int = -1,
    var title: String = "",
    var url: String = "",
    var height: Int = 0,
    var width: Int = 0
): Parcelable {

    fun getLayoutId() = layoutId

    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readInt(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(layoutId)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeInt(height)
        parcel.writeInt(width)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Photo> {
        override fun createFromParcel(parcel: Parcel): Photo {
            return Photo(parcel)
        }

        override fun newArray(size: Int): Array<Photo?> {
            return arrayOfNulls(size)
        }
    }

}