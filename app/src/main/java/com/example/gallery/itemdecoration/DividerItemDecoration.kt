package com.example.gallery.itemdecoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.R

class DividerItemDecoration(context: Context): RecyclerView.ItemDecoration() {

    private val dividerSpace = context.resources.getDimensionPixelOffset(R.dimen.divider_space)
    private val divider = ContextCompat.getDrawable(context, R.drawable.divider)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0

        with(outRect) {
            if (position == 0) {
                top = dividerSpace
            }
            bottom = dividerSpace
        }
    }


}