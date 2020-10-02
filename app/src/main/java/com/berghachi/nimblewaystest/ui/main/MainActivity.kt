package com.berghachi.nimblewaystest.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.berghachi.nimblewaystest.R
import com.berghachi.nimblewaystest.utils.Status
import com.berghachi.nimblewaystest.utils.adapters.RepoAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {


    @Inject
    lateinit var factory: ViewModelProvider.Factory

    var repoAdapter: RepoAdapter? = null
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, factory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initEvents()
        initUi()
        loadData()
        observeScroll()

    }

    private fun initUi() {
        repoAdapter = RepoAdapter(mainViewModel,arrayListOf(), onFavoriClick = { repo, pos ->

            repo?.let {
                if(repo.isFavorite){
                    mainViewModel.deleteRepoFromFavoris(it,pos).observe(this, Observer {
                        if (it.first != -1) {
                            Timber.d("deleted")
                            repoAdapter?.notifyItemChanged(pos)
                        }
                    })
                }else {
                    mainViewModel.addRepotoFavoris(it,pos).observe(this, Observer {
                        if (it.first != -1L) {
                            Timber.d("added")
                            repoAdapter?.notifyItemChanged(pos)
                        }
                    })
                }

            }
        })

        recycler_view_repo.adapter = repoAdapter

    }

    private fun observeScroll() {

        recycler_view_repo.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    loadData()
                }

            }

        })
    }

    private fun loadData() {
        if (mainViewModel.isLastPage) {
            Toast.makeText(this, getString(R.string.end_of_list), Toast.LENGTH_SHORT).show()
        } else {
            mainViewModel.getRepos(mainViewModel.pageIndex).observe(this, Observer {
                when (it.status) {
                    Status.LOADING -> {
                        swipe_refresh_recycler_repo.isRefreshing = true
                    }
                    Status.ERROR -> {
                        swipe_refresh_recycler_repo.isRefreshing = false
                    }
                    Status.SUCCESS -> {
                        swipe_refresh_recycler_repo.isRefreshing = false
                        repoAdapter?.submitList(it.data, mainViewModel.pageIndex)
                        Timber.e(it.data.toString())

                    }
                }
            })
        }

    }

    private fun initEvents() {
        swipe_refresh_recycler_repo.setOnRefreshListener {
            mainViewModel.pageIndex = 1
            mainViewModel.isLastPage = false
            loadData()
        }
    }
}