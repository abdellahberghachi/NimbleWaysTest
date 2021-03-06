package com.berghachi.nimblewaystest.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berghachi.nimblewaystest.data.local.model.Repo
import com.berghachi.nimblewaystest.data.repository.Repository
import com.berghachi.nimblewaystest.utils.Resource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    var pageIndex = 1
    var isLastPage = false

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


    fun getRepos(page: Int): MutableLiveData<Resource<List<Repo>>> {
        val response: MutableLiveData<Resource<List<Repo>>> = MutableLiveData()

        compositeDisposable.add(
            repository.getRepos(page)
                .subscribeOn(
                    Schedulers.io()
                )
                .doOnSubscribe {
                    response.postValue(Resource.loading(null))
                }.subscribe(
                    { respo ->
                        if (respo.isEmpty()) {
                            Timber.d("isLastpage $pageIndex")
                            isLastPage = true
                        }
                        pageIndex++
                        Timber.d("$pageIndex")
                        response.postValue(Resource.success(respo))

                    }, { error ->
                        Timber.e(error.toString())
                        response.postValue(
                            Resource.error(
                                error?.localizedMessage.toString(),
                                null
                            )
                        )
                    })
        )
        return response
    }

    fun addRepotoFavoris(repo: Repo, position: Int): MutableLiveData<Pair<Long, Int>> {
        val returnedVal = MutableLiveData<Pair<Long, Int>>()
        viewModelScope.launch {
            returnedVal.postValue(Pair(repository.addRepotoFavoris(repo), position))
        }
        return returnedVal
    }

    fun deleteRepoFromFavoris(repo: Repo, position: Int): MutableLiveData<Pair<Int, Int>> {
        val returnedVal = MutableLiveData<Pair<Int, Int>>()
        viewModelScope.launch {
            returnedVal.postValue(Pair(repository.deleteRepoFromFavoris(repo), position))
        }
        return returnedVal
    }

    suspend fun isFavoriteRepos(repo: Repo): Boolean {
        return repository.isFavoriteRepos(repo.id)
    }

}