package com.dmitryshuba.sample.service.network

import com.dmitryshuba.sample.service.network.model.response.SectionPageResponse

interface INetworkService {

    suspend fun fetchSectionPage(href: String): NetworkRequestResult<SectionPageResponse>
}