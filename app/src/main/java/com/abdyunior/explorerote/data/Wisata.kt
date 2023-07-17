package com.abdyunior.explorerote.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Wisata(
    val name: String,
    val description: String,
    val photo: Int,
    val location: String,
    val hrgTicket: String,
    val lat: Double,
    val lng: Double,
    val phoneNumber: String,
    val email: String
) : Parcelable
