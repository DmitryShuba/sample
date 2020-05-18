package com.dmitryshuba.sample.service.network.model.response

import com.google.gson.annotations.SerializedName

data class LinkArray(@SerializedName("CENSORED:sections") val sections: List<Section>)