package com.dmitryshuba.sample.service.network.util.connectionservice

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ConnectionService(private val context: Context) : IConnectionService {
    private val mConnectionStatusObservable = MutableLiveData<ConnectionStatus>()
        .apply { value = (ConnectionStatus.UNDEFINED) }
    override val connectionStatusObservable: LiveData<ConnectionStatus>
        get() = mConnectionStatusObservable

    init {
        registerConnectionUpdates()
    }

    private fun registerConnectionUpdates() {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        val builder = NetworkRequest.Builder()

        connectivityManager!!.registerNetworkCallback(
            builder.build(),
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    mConnectionStatusObservable.postValue(ConnectionStatus.CONNECTED)
                }

                override fun onLost(network: Network) {
                    if (!isNetworkConnected()) {
                        mConnectionStatusObservable.postValue(ConnectionStatus.DISCONNECTED)
                    }
                }
            }
        )
    }

    private fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}
