package com.dmitryshuba.sample.main.sectionlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmitryshuba.sample.main.sectionlist.adapter.props.SectionProps
import com.dmitryshuba.sample.main.sectionlist.props.SectionDataProps
import com.dmitryshuba.sample.service.database.model.SectionPageEntity
import com.dmitryshuba.sample.service.network.util.connectionservice.ConnectionStatus
import com.dmitryshuba.sample.service.network.util.connectionservice.IConnectionService

class SectionListViewModel(
    private val repository: ISectionListRepository,
    private val connectionService: IConnectionService
) : ISectionListViewModel, ViewModel() {

    private lateinit var currentPageHref: String

    private val mLoaderVisibility = MutableLiveData<Boolean>()
    override val loaderVisibility: LiveData<Boolean>
        get() = mLoaderVisibility

    private val mSectionDataPropsObservable = MutableLiveData<SectionDataProps>()
    val sectionDataPropsObservable: LiveData<SectionDataProps>
        get() = mSectionDataPropsObservable

    private val mIsPageAvailableLiveData = MutableLiveData<Boolean>().apply { value = true }
    override val isPageAvailableLiveData: LiveData<Boolean>
        get() = mIsPageAvailableLiveData

    val stateObservable = MediatorLiveData<SectionListViewModelState>().apply {
        addSource(repository.repositoryStateObservable) {
            handleRepositoryState(it)
        }
        addSource(repository.localSectionDataObservable) {
            processLocalSectionPageList(it)
        }
        addSource(connectionService.connectionStatusObservable) {
            processConnectionStatus(it)
        }
    }

    fun fetchSectionList(href: String) {
        currentPageHref = href
        repository.fetchSections(href)
    }

    private fun processConnectionStatus(status: ConnectionStatus?) {
        when (status) {
            ConnectionStatus.CONNECTED -> {
                if (!mIsPageAvailableLiveData.value!!) {
                    fetchSectionList(currentPageHref)
                    stateObservable.value = SectionListViewModelState.ACTION_SHOW_NETWORK_IS_ON
                    mIsPageAvailableLiveData.value = true
                }
            }
            else -> Unit
        }
    }

    private fun processLocalSectionPageList(list: List<SectionPageEntity>?) {
        val item = list?.find { it.href == currentPageHref }
        item?.let {
            convertAndPassSectionEntityToProps(item)
        } ?: if (connectionService.connectionStatusObservable.value != ConnectionStatus.CONNECTED) {
            stateObservable.value = SectionListViewModelState.ACTION_SHOW_NO_NETWORK_WARNING
            mIsPageAvailableLiveData.value = false
        }
    }

    private fun handleRepositoryState(state: SelectionListRepositoryState) {
        when (state) {
            SelectionListRepositoryState.REQUEST_START -> mLoaderVisibility.value = true
            SelectionListRepositoryState.RESPONSE_SUCCESS -> Unit
            SelectionListRepositoryState.RESPONSE_FAILURE -> stateObservable.value =
                SectionListViewModelState.ACTION_SHOW_NETWORK_ERROR_WARNING
            SelectionListRepositoryState.REQUEST_FINISH -> mLoaderVisibility.value = false
        }
    }

    private fun convertAndPassSectionEntityToProps(entity: SectionPageEntity) {
        entity.let {
            mSectionDataPropsObservable.value = SectionDataProps(it.title,
                it.description,
                it.sectionList.map { e -> SectionProps(e.title, e.href) })
        }
    }
}