package com.berghachi.nimblewaystest.data.repository

import com.berghachi.nimblewaystest.data.local.LocalDataSource
import com.berghachi.nimblewaystest.data.local.model.Repo
import com.berghachi.nimblewaystest.data.remote.IRemoteService
import io.reactivex.Single

class Repository(
    val remoteDataSource: IRemoteService,
    val localDataSource: LocalDataSource
) {

    fun getRepos(page:Int): Single<List<Repo>> {

        return remoteDataSource.getReposByPage(page)
    }

    suspend fun addRepotoFavoris(repo: Repo): Long {
        return localDataSource.getRepoDao().addRepoToFavorite(repo)
    }
    suspend fun deleteRepoFromFavoris(repo: Repo): Int {
        return localDataSource.getRepoDao().deleteRepo(repo)
    }

    suspend fun isFavoriteRepos(id: Int): Boolean {
        return localDataSource.getRepoDao().isRepoIsExist(id)
    }


}