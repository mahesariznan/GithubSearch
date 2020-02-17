package com.iznan.githubsearch.util

import androidx.recyclerview.widget.RecyclerView

abstract class OnVerticalScrollListener : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (!recyclerView.canScrollVertically(-1)) {
            onScrolledToVeryTop(recyclerView)

        } else if (!recyclerView.canScrollVertically(1)) {
            onScrolledToVeryBottom(recyclerView)

        }
    }

    abstract fun onScrolledToVeryTop(recyclerView: RecyclerView)

    abstract fun onScrolledToVeryBottom(recyclerView: RecyclerView)
}