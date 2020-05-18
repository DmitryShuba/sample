package com.dmitryshuba.sample.service.network.api

interface INetworkApiProvider {

    fun getMainApi(): IMainApi
}