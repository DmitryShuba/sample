package com.dmitryshuba.sample

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.dmitryshuba.sample.main.sectionlist.util.sectionListModule
import com.dmitryshuba.sample.main.util.ViewModelFactory
import com.dmitryshuba.sample.service.database.DatabaseProvider
import com.dmitryshuba.sample.service.database.IDatabaseProvider
import com.dmitryshuba.sample.service.network.INetworkService
import com.dmitryshuba.sample.service.network.NetworkService
import com.dmitryshuba.sample.service.network.api.INetworkApiProvider
import com.dmitryshuba.sample.service.network.api.NetworkApiProvider
import com.dmitryshuba.sample.service.network.util.connectionservice.ConnectionService
import com.dmitryshuba.sample.service.network.util.connectionservice.IConnectionService
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.*

class SampleApplication : Application(), KodeinAware {

    companion object {
        private const val DB_NAME = "SampleDatabase"
        private const val MAIN_MODULE_NAME = "kodeinModuleMain"
    }

    private val mainModule = Kodein.Module(MAIN_MODULE_NAME) {
        bind<Context>() with singleton { this@SampleApplication }

        bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(this) }

        bind<INetworkApiProvider>() with provider {
            NetworkApiProvider()
        }

        bind<IDatabaseProvider>() with eagerSingleton {
            Room.databaseBuilder(
                instance(),
                DatabaseProvider::class.java,
                DB_NAME
            ).fallbackToDestructiveMigration().build()
        }

        bind<INetworkService>() with provider {
            NetworkService(instance())
        }

        bind<IConnectionService>() with singleton {
            ConnectionService(instance())
        }
    }

    override val kodein = Kodein.lazy {
        import(mainModule)
        import(sectionListModule)
    }
}