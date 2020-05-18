package com.dmitryshuba.sample.service.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dmitryshuba.sample.service.database.model.SectionPageEntity

@Database(
    entities = [SectionPageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DatabaseProvider : RoomDatabase(), IDatabaseProvider