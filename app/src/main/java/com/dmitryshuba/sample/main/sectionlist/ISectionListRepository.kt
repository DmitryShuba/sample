package com.dmitryshuba.sample.main.sectionlist

import androidx.lifecycle.LiveData
import com.dmitryshuba.sample.service.database.model.SectionPageEntity

interface ISectionListRepository {

    val repositoryStateObservable: LiveData<SelectionListRepositoryState>

    val localSectionDataObservable: LiveData<List<SectionPageEntity>>

    fun fetchSections(href: String)
}