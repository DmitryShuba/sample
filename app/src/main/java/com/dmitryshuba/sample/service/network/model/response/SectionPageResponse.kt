package com.dmitryshuba.sample.service.network.model.response

import com.google.gson.annotations.SerializedName

data class SectionPageResponse(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("_links") val links: LinkArray
)