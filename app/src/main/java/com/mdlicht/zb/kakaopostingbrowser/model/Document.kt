package com.mdlicht.zb.kakaopostingbrowser.model

import android.os.Parcel
import android.os.Parcelable

data class Document(
    val blogname: String?,
    val cafename: String?,
    val contents: String?,
    val datetime: String?,
    val thumbnail: String?,
    val title: String?,
    val url: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(blogname)
        parcel.writeString(cafename)
        parcel.writeString(contents)
        parcel.writeString(datetime)
        parcel.writeString(thumbnail)
        parcel.writeString(title)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Document> {
        override fun createFromParcel(parcel: Parcel): Document {
            return Document(parcel)
        }

        override fun newArray(size: Int): Array<Document?> {
            return arrayOfNulls(size)
        }
    }
}