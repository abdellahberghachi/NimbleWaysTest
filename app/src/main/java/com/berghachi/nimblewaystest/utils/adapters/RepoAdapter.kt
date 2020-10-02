package com.berghachi.nimblewaystest.utils.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.berghachi.nimblewaystest.R
import com.berghachi.nimblewaystest.data.local.model.Repo
import com.berghachi.nimblewaystest.ui.main.MainViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class RepoAdapter(
    val mainViewModel: MainViewModel,
    private var repos: MutableList<Repo>?, val onFavoriClick: (Repo?, position: Int) -> Unit
) : RecyclerView.Adapter<RepoAdapter.MealViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepoAdapter.MealViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.repo_item, parent, false)
        return MealViewHolder(view)
    }

    override fun getItemCount(): Int {
        return repos?.size ?: 0
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val repo = repos?.get(position)


        Glide.with(holder.itemView.context).load(repo?.owner?.avatarUrl).into(holder.ivOwner)

        holder.repoURL.text = repo?.owner?.url
        holder.repoName.text = repo?.name
        holder.repoLanguage.text = repo?.language

        holder.ivFavori.setOnClickListener {
            onFavoriClick(repo, position)
        }
        GlobalScope.launch {
                repo?.let {
                    val isFavorite = mainViewModel.isFavoriteRepos(it)
                    repo.isFavorite=isFavorite
                    if (isFavorite) {
                        holder.ivFavori.background = ContextCompat.getDrawable(
                            holder.itemView.context,
                            R.drawable.ic_favori_full
                        )
                    } else {
                        holder.ivFavori.background = ContextCompat.getDrawable(
                            holder.itemView.context,
                            R.drawable.ic_favori_empty
                        )
                    }
                }
        }


    }

    fun submitList(data: List<Repo>?, pageIndex: Int) {
        if (pageIndex == 1) {
            repos = data as MutableList<Repo>?
            notifyDataSetChanged()
        } else {
            repos?.let { data?.let { it1 -> repos?.addAll(repos?.size ?: 0, it1) } }
            notifyItemRangeInserted(repos?.size ?: 0 + 1, data?.size ?: 0)
        }
    }


    class MealViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val ivOwner = view.findViewById<ImageView>(R.id.iv_owner)
        val repoURL = view.findViewById<TextView>(R.id.txt_url)
        val repoName = view.findViewById<TextView>(R.id.txt_repo_name)
        val repoLanguage = view.findViewById<TextView>(R.id.txt_language)
        val ivFavori = view.findViewById<ImageView>(R.id.iv_favoris)


    }

}




