package com.berghachi.nimblewaystest.data.local.dao

import androidx.room.*
import com.berghachi.nimblewaystest.data.local.model.Repo


@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRepoToFavorite(repo: Repo): Long

    // remove repo from database ( unfavori)
    @Delete
    suspend fun deleteRepo(repo: Repo): Int


    @Query("SELECT EXISTS(SELECT * FROM repo WHERE id = :id)")
    suspend fun isRepoIsExist(id : Int) : Boolean
}