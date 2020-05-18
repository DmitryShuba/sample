package com.dmitryshuba.sample.main.sectionlist.props

import com.dmitryshuba.sample.main.sectionlist.adapter.props.SectionProps

data class SectionDataProps(
    val header: String,
    val description: String,
    val sectionPropsList: List<SectionProps>
)