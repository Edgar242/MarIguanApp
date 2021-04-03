package com.mar_iguana.tours.models

import android.os.Parcel
import android.os.Parcelable

data class Tour(
    val id: Int,
    val title: String?,
    val price: Float,
    val dates: String?,
    val rating: Float,
    val images: List<Int>,
    val info: String?,
    val itinerary: String?,
    val promo:String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readString(),
        parcel.readFloat(),
        TODO("images"),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeFloat(price)
        parcel.writeString(dates)
        parcel.writeFloat(rating)
        parcel.writeString(info)
        parcel.writeString(itinerary)
        parcel.writeString(promo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Tour> {
        override fun createFromParcel(parcel: Parcel): Tour {
            return Tour(parcel)
        }

        override fun newArray(size: Int): Array<Tour?> {
            return arrayOfNulls(size)
        }
    }
}