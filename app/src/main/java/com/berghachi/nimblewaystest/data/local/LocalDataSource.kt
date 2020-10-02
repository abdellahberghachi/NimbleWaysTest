package com.berghachi.nimblewaystest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.berghachi.nimblewaystest.data.local.dao.RepoDao
import com.berghachi.nimblewaystest.data.local.model.Repo


@Database(entities = [Repo::class], version = 1,exportSchema = false)
abstract class LocalDataSource : RoomDatabase() {

    abstract fun getRepoDao():RepoDao

}