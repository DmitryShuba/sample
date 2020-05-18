package com.dmitryshuba.sample.main.sectionlist.util

import com.dmitryshuba.sample.main.sectionlist.ISectionListRepository
import com.dmitryshuba.sample.main.sectionlist.SectionListRepository
import com.dmitryshuba.sample.main.sectionlist.SectionListViewModel
import com.dmitryshuba.sample.main.util.bindViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

private const val SECTION_LIST_MODULE_NAME = "KodeinModuleSectionList"

val sectionListModule = Kodein.Module(SECTION_LIST_MODULE_NAME) {

    bind<ISectionListRepository>() with provider {
        SectionListRepository(instance(), instance())
    }

    bindViewModel<SectionListViewModel>() with provider {
        SectionListViewModel(instance(), instance())
    }
}