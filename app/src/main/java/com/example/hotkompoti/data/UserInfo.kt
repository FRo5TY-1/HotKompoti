package com.example.hotkompoti.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfo(
    val uid: String,
    val pictureUrl: String,
    val firstName: String,
    val lastName: String,
    val country: String,
    val number: String
) : Parcelable{
    constructor() : this (
        "",
        "",
        "",
        "",
        "",
        ""
    )
}
