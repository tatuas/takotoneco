package com.tatuas.takotoneco.view

import androidx.recyclerview.widget.RecyclerView

class RecyclerViewVerticalScrollListener(
    private val onScrollEnd: (() -> Unit)? = null
) : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        val isScrollable = recyclerView.computeVerticalScrollRange() > recyclerView.height

        if (isScrollable &&
            newState == RecyclerView.SCROLL_STATE_IDLE &&
            recyclerView.canScrollVertically(-1)
        ) {
            onScrollEnd?.invoke()
        }
    }
}
