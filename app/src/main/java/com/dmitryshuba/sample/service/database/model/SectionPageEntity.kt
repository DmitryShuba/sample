package com.dmitryshuba.sample.service.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dmitryshuba.sample.service.database.util.SectionPageConverter

@Entity
@TypeConverters(SectionPageConverter::class)
data class SectionPageEntity(
    @PrimaryKey
    val href: String,
    val title: String,
    val description: String,
    val sectionList: List<SectionEntity>
)