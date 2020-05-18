package com.dmitryshuba.sample.service.network.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class NetworkApiProvider : INetworkApiProvider, Interceptor {

    companion object {
        private const val BASE_URL = "CENSORED"
        private const val TIMEOUT_DELAY_SECONDS = 45L
    }

    init {
        initApi()
    }

    private lateinit var mMainApi: IMainApi

    override fun getMainApi(): IMainApi = mMainApi

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .header("Accept-Language", Locale.getDefault().toString())
            .method(original.method, original.body)
            .build()

        return chain.proceed(request)
    }

    private fun initApi() {
        mMainApi = getRetrofit().create(IMainApi::class.java)
    }

    private fun getLoggingClient(): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }).addInterceptor(this@NetworkApiProvider)
            .connectTimeout(TIMEOUT_DELAY_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_DELAY_SECONDS, TimeUnit.SECONDS)
            .build()

    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getLoggingClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}
