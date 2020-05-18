package com.dmitryshuba.sample.service.network.api

import com.dmitryshuba.sample.service.network.model.response.SectionPageResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

interface IMainApi {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET
    fun fetchSectionPageAsync(@Url url: String): Deferred<Response<SectionPageResponse>>
}