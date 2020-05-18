package com.dmitryshuba.sample.service.network

import com.dmitryshuba.sample.service.network.api.INetworkApiProvider
import com.dmitryshuba.sample.service.network.model.response.SectionPageResponse

class NetworkService(private val networkProvider: INetworkApiProvider) : INetworkService {

    companion object {
        const val DOWNLOADABLE_SUFFIX = "{?dtg}"
    }

    override suspend fun fetchSectionPage(href: String): NetworkRequestResult<SectionPageResponse> {
        return networkProvider
            .getMainApi()
            .fetchSectionPageAsync(href.removeSuffix(DOWNLOADABLE_SUFFIX))
            .performSafeApiCallResult()
    }
}