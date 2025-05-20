package com.example.categories.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

internal class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int,
    private val includeEdge: Boolean,
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        val left = spacing * column / spanCount
        val right = spacing * (spanCount - 1 - column) / spanCount

        if (includeEdge) {
            outRect.left = spacing - left
            outRect.right = spacing - right
            outRect.top = if (position < spanCount) spacing else 0
            outRect.bottom = spacing
        } else {
            outRect.left = left
            outRect.right = right
            outRect.top = if (position >= spanCount) spacing else 0
        }
    }
}
