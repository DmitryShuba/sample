package com.dmitryshuba.sample.main.sectionlist.util

import androidx.navigation.NavController
import com.dmitryshuba.sample.main.sectionlist.SectionListFragmentDirections

class SectionListRouter(private val navController: NavController) : ISectionListRouter {

    override fun navigateToSectionDetails(href: String) {
        val direction = SectionListFragmentDirections.actionSectionListFragmentSelf()
            .setHrefArg(href)
            .setBackButtonAvailableArg(true)
        navController.navigate(direction)
    }
}