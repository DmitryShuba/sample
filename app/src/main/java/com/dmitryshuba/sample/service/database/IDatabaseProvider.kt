package com.dmitryshuba.sample.service.database

import com.dmitryshuba.sample.service.database.dao.IMainDao

interface IDatabaseProvider {

    fun getMainDao(): IMainDao
}
