package com.berghachi.nimblewaystest.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.berghachi.nimblewaystest.data.local.model.Repo


@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRepoToFavorite(repo: Repo): Long

    // remove repo from database ( unfavori)
    @Delete
    suspend fun deleteRepo(repo: Repo): Int
}