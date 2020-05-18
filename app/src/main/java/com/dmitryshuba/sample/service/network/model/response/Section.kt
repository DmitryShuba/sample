package com.dmitryshuba.sample.service.network.model.response

import com.google.gson.annotations.SerializedName

data class Section(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("href") val href: String,
    @SerializedName("type") val type: String,
    @SerializedName("name") val name: String,
    @SerializedName("templated") val templated: Boolean,
    @SerializedName("active") val active: Boolean
)