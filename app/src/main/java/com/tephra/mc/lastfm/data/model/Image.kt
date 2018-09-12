package com.tephra.mc.lastfm.data.model

import com.google.gson.annotations.SerializedName


data class Image (@SerializedName("#text") val imageUrl: String,
             val size: String)
