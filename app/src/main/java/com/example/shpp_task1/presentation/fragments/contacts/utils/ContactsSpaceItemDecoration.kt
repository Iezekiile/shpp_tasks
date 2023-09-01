package com.example.shpp_task1.presentation.fragments.contacts.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Custom ItemDecoration class for adding space between items in a RecyclerView.
 *
 * @param space The amount of space to add (in pixels) between items.
 */
class ContactsSpaceItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    /**
     * Called to determine the size and positioning of the space for an item in the RecyclerView.
     *
     * @param outRect The rectangle representing the space to add around the item.
     * @param view The view of the item.
     * @param parent The parent RecyclerView.
     * @param state The current state of the RecyclerView.
     */
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        // Add bottom spacing to all items
        outRect.bottom = space

        // Add top spacing only to the first item
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space
        }
    }
}