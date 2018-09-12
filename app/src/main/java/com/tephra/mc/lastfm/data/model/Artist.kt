package com.tephra.mc.lastfm.data.model

import com.google.gson.annotations.SerializedName

data class Artist (val name: String,
              val listeners: String,
              val mbid: String,
              val url: String,
              val streamable: String,
              @SerializedName("image") val images: List<Image>
              )