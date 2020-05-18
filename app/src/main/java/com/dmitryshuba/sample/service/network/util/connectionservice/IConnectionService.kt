package com.dmitryshuba.sample.service.network.util.connectionservice

import androidx.lifecycle.LiveData

interface IConnectionService {

    val connectionStatusObservable: LiveData<ConnectionStatus>
}
