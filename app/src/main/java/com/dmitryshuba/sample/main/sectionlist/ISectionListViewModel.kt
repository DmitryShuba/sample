package com.dmitryshuba.sample.main.sectionlist

import androidx.lifecycle.LiveData

interface ISectionListViewModel {

    val isPageAvailableLiveData: LiveData<Boolean>

    val loaderVisibility: LiveData<Boolean>
}