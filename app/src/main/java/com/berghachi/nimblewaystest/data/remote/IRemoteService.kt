package com.berghachi.nimblewaystest.data.remote

import com.berghachi.nimblewaystest.data.local.model.Repo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface IRemoteService {


    @GET("/orgs/bamlab/repos")
    fun getReposByPage(
        @Query("page") page: Int,
    ): Single<List<Repo>>
}