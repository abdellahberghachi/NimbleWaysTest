package com.berghachi.nimblewaystest.di.modules

import androidx.room.Room
import com.berghachi.nimblewaystest.BuildConfig
import com.berghachi.nimblewaystest.base.BaseApplication
import com.berghachi.nimblewaystest.data.local.DbConstant
import com.berghachi.nimblewaystest.data.local.LocalDataSource
import com.berghachi.nimblewaystest.data.remote.IRemoteService
import com.berghachi.nimblewaystest.data.repository.Repository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideLocalDb(baseApplication: BaseApplication) : LocalDataSource {
        return Room.databaseBuilder(baseApplication, LocalDataSource::class.java, DbConstant.DB_NAME).build()
    }

    @Singleton
    @Provides
    fun providesRemoteService(okHttpClient: OkHttpClient.Builder): IRemoteService {
        return Retrofit.Builder()
            .client(okHttpClient.build())
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IRemoteService::class.java)
    }

    @Singleton
    @Provides
    fun providesRepositoryDataSource(localDataSource: LocalDataSource, remoteDataSource: IRemoteService): Repository {
        return Repository(remoteDataSource, localDataSource)
    }



    @Singleton
    @Provides
    fun okHTPP(): OkHttpClient.Builder {
        return OkHttpClient.Builder().readTimeout(120, TimeUnit.SECONDS)
    }




}