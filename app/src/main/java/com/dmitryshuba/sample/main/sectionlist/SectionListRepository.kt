package com.dmitryshuba.sample.main.sectionlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dmitryshuba.sample.service.database.IDatabaseProvider
import com.dmitryshuba.sample.service.database.model.SectionEntity
import com.dmitryshuba.sample.service.database.model.SectionPageEntity
import com.dmitryshuba.sample.service.network.INetworkService
import com.dmitryshuba.sample.service.network.NetworkRequestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SectionListRepository(
    private val networkService: INetworkService,
    private val persistence: IDatabaseProvider
) : ISectionListRepository {

    private val mRepositoryStateObservable = MutableLiveData<SelectionListRepositoryState>()
    override val repositoryStateObservable: LiveData<SelectionListRepositoryState>
        get() = mRepositoryStateObservable

    override val localSectionDataObservable = persistence.getMainDao().getAllSelectionPages()

    override fun fetchSections(href: String) {
        mRepositoryStateObservable.value = SelectionListRepositoryState.REQUEST_START
        GlobalScope.launch(Dispatchers.IO) {
            val result = networkService.fetchSectionPage(href)
            withContext(Dispatchers.Main) {
                when (result) {
                    is NetworkRequestResult.Success -> {
                        insertSectionToDatabase(
                            SectionPageEntity(
                                href,
                                result.data.title,
                                result.data.description,
                                result.data.links.sections
                                    .map { SectionEntity(it.href, it.title) })
                        )
                        mRepositoryStateObservable.value =
                            SelectionListRepositoryState.RESPONSE_SUCCESS
                    }
                    is NetworkRequestResult.Failure -> {
                        mRepositoryStateObservable.value =
                            SelectionListRepositoryState.RESPONSE_FAILURE
                    }
                }
                mRepositoryStateObservable.value = SelectionListRepositoryState.REQUEST_FINISH
            }
        }
    }

    private suspend fun insertSectionToDatabase(entity: SectionPageEntity) {
        withContext(Dispatchers.IO) {
            persistence.getMainDao().insertSectionPageEntity(entity)
        }
    }
}