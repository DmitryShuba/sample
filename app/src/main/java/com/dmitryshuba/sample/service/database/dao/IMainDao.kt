package com.dmitryshuba.sample.service.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitryshuba.sample.service.database.model.SectionPageEntity

@Dao
interface IMainDao {

    @Query("SELECT * FROM SectionPageEntity")
    fun getAllSelectionPages(): LiveData<List<SectionPageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSectionPageEntity(sectionPageEntity: SectionPageEntity)
}